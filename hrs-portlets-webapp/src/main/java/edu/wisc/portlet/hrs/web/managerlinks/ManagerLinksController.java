package edu.wisc.portlet.hrs.web.managerlinks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.hr.dao.url.HrsUrlDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import edu.wisc.portlet.hrs.web.listoflinks.Link;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import org.apache.commons.lang.StringUtils;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * Exposes list-of-links JSON suitable for presenting time approval links to managers.
 */
@Controller
@RequestMapping("VIEW")
public class ManagerLinksController
  extends HrsControllerBase {

  /**
   * Default user-facing label for the approvals dashboard link.
   * @deprecated instead use the app-specific or widget-specific label.
   */
  public static String DEFAULT_DASHBOARD_LABEL = "Time/Absence Dashboard";

  /**
   * Default user-facing label for the approvals dashboard link,
   * as surfaced in the Manager Time and Approval app.
   */
  public static String DEFAULT_DASHBOARD_LABEL_APP = "Time/Absence Dashboard (Approve Time)";

    /**
   * Default user-facing label for the approvals dashboard link,
   * as surfaced in the Manager Time and Approval widget.
   */
  public static String DEFAULT_DASHBOARD_LABEL_WIDGET = "Time/Absence Dashboard";

  /**
   * A strict reading of the MyUW style guidance wrt list-of-links apps would have this sentence
   * case, but title case is as requested.
   */
  public static String DEFAULT_APPROVE_ABSENCE_LABEL = "Approve Absence";

  /**
   * A strict reading of the MyUW style guidance wrt list-of-links apps would have this sentence
   * case, but title case is as requested.
   */
  public static String DEFAULT_APPROVE_TIME_LABEL = "Approve Time";

  private static Set<String> ROLES_THAT_MANAGE_TIME_OR_ABSENCES;

  static {
    final Set<String> rolesWithApprovalAccess = new HashSet<String>();
    rolesWithApprovalAccess.add("ROLE_VIEW_MANAGED_ABSENCES");
    rolesWithApprovalAccess.add("ROLE_VIEW_MANAGED_TIMES");
    rolesWithApprovalAccess.add("ROLE_VIEW_TIME_ABS_DASHBOARD");

    ROLES_THAT_MANAGE_TIME_OR_ABSENCES = Collections.unmodifiableSet(rolesWithApprovalAccess);
  }

  private HrsRolesDao rolesDao;

  public HrsRolesDao getRolesDao() {
    return rolesDao;
  }

  @Autowired
  public void setRolesDao(HrsRolesDao rolesDao) {
    this.rolesDao = rolesDao;
  }

  /**
   * Model is "content" --> "links" --> Link[],
   * suitable for (in JSON representation) use as the remote source for dynamic list-of-links
   * widget in uPortal-app-framework.
   * @param modelMap
   * @return String representing view
   */
  @ResourceMapping("managerListOfLinks")
  public String managerTimeAndApprovalListOfLinks(ModelMap modelMap, PortletRequest request) {

    final String emplId = PrimaryAttributeUtils.getPrimaryId();
    final Set<String> roles = Collections.unmodifiableSet(this.rolesDao.getHrsRoles(emplId));

    final PortletPreferences preferences = request.getPreferences();
    final String approvalsDashboardUrl = approvalsDashboardUrl(preferences);
    final String approvalsDashboardLabel =
        preferences.getValue("approvalsDashboardLabel", DEFAULT_DASHBOARD_LABEL_WIDGET);
    final String approveAbsenceLabel =
        preferences.getValue("approveAbsenceLabel", DEFAULT_APPROVE_ABSENCE_LABEL);
    final String approveTimeLabel =
        preferences.getValue("approveTimeLabel", DEFAULT_APPROVE_TIME_LABEL);
    final String helpUrl = preferences.getValue("helpUrl", null);


    final List<Link> linkList = new ArrayList<Link>();

    if (Sets.intersection(roles, ROLES_THAT_MANAGE_TIME_OR_ABSENCES).isEmpty()) {
      if (StringUtils.isNotBlank(helpUrl)){
        // No relevant roles. Add error links.
        final Link youAreNotAManager = new Link();
        youAreNotAManager.setTitle("Not a manager");
        youAreNotAManager.setIcon("not_interested");
        youAreNotAManager.setHref(helpUrl);
        youAreNotAManager.setTarget("_blank");
        linkList.add(youAreNotAManager);

        final Link helpdesk = new Link();
        helpdesk.setTitle("Get help");
        helpdesk.setIcon("help");
        helpdesk.setHref(helpUrl);
        helpdesk.setTarget("_blank");
        linkList.add(helpdesk);
      } else {
        logger.error("Portlet preference [helpUrl] expected but not found "
            + "so could not be offered to " + emplId);
      }
    }

    if (roles.contains("ROLE_VIEW_TIME_ABS_DASHBOARD")) {
      if (StringUtils.isNotBlank(approvalsDashboardUrl)) {
        final Link approvalsDashboard = new Link();
        approvalsDashboard.setTitle(approvalsDashboardLabel);
        approvalsDashboard.setIcon("check_circle");
        approvalsDashboard.setHref(approvalsDashboardUrl);
        approvalsDashboard.setTarget("_blank");
        linkList.add(approvalsDashboard);
      } else {
        logger.error("Portlet preference [approvalsDashboardUrl] "
          + "or HRS URLS DAO url " + HrsUrlDao.TIME_ABSENCE_DASHBOARD_KEY + " "
          + "expected but not found "
          + "and so could not be offered to " + emplId);
      }
    }

    if (roles.contains("ROLE_VIEW_MANAGED_ABSENCES")) {
      final String approveAbsenceUrl = getHrsUrls().get("Approve Absence");
      if (StringUtils.isNotBlank(approveAbsenceUrl)) {
        final Link approveAbsence = new Link();
        approveAbsence.setTitle(approveAbsenceLabel);
        approveAbsence.setIcon("perm_contact_calendar");
        approveAbsence.setHref(approveAbsenceUrl);
        approveAbsence.setTarget("_blank");
        linkList.add(approveAbsence);
      } else {
        logger.error("HRS URL [Approve Absence] expected but not found "
            + "and so could not be offered to emplid " + emplId);
      }
    }

    final String approveTimeUrl = getHrsUrls().get(HrsUrlDao.APPROVE_PAYABLE_TIME_KEY);
    if (StringUtils.isNotBlank(approveTimeUrl)
      && roles.contains("ROLE_VIEW_MANAGED_TIMES")) {

      final Link approveTime = new Link();
      approveTime.setTitle(approveTimeLabel);
      approveTime.setIcon("access_time");
      approveTime.setHref(approveTimeUrl);
      approveTime.setTarget("_blank");

      linkList.add(approveTime);
    }

    final Map<String, Object[]> content = new HashMap<String, Object[]>();
    content.put("links", linkList.toArray());

    modelMap.put("content", content);

    return "contentAttrJsonView";
  }


  @RequestMapping
  public String viewLinks( ModelMap modelMap, PortletRequest request){

    final String emplId = PrimaryAttributeUtils.getPrimaryId();

    final PortletPreferences preferences = request.getPreferences();

    final String approvalsDashboardUrl = approvalsDashboardUrl(preferences);

    final String approvalsDashboardLabel =
        preferences.getValue("approvalsDashboardLabel", DEFAULT_DASHBOARD_LABEL_APP);
    final String approveAbsenceLabel =
        preferences.getValue("approveAbsenceLabel", DEFAULT_APPROVE_ABSENCE_LABEL);
    final String approveTimeLabel =
        preferences.getValue("approveTimeLabel", DEFAULT_APPROVE_TIME_LABEL);

    modelMap.put("approvalsDashboardUrl", approvalsDashboardUrl);
    modelMap.put("approvalsDashboardLabel", approvalsDashboardLabel);
    modelMap.put("approveAbsenceLabel", approveAbsenceLabel);
    modelMap.put("approveTimeLabel", approveTimeLabel);

    return "managerLinks";
  }

  /**
   * Returns the approvals dashboard URL, preferring that from the HRS URLs
   * DAO, falling back on a Portlet Preference, and returning null if neither is
   * available.
   *
   * @param preferences the PortletPreferences
   * @return the preferred approvals dashboard URL, or null if not set
   */
  public String approvalsDashboardUrl(PortletPreferences preferences) {
    String approvalsDashboardUrl =
      this.getHrsUrls().get(HrsUrlDao.TIME_ABSENCE_DASHBOARD_KEY);

    if (null == approvalsDashboardUrl) {
      approvalsDashboardUrl =
        preferences.getValue("approvalsDashboardUrl", null);
      // this sourcing approvals dashboard URL from portlet-preference is
      // DEPRECATED and will be removed in a future release.
    }

    return approvalsDashboardUrl;
  }

}
