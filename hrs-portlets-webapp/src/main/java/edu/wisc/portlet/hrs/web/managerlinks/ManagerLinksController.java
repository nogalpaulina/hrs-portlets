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

  private static Set<String> ROLES_THAT_MANAGE_TIME_OR_ABSENCES;

  static {
    final Set<String> rolesWithApprovalAccess = new HashSet<String>();
    rolesWithApprovalAccess.add("ROLE_VIEW_MANAGED_ABSENCES");
    rolesWithApprovalAccess.add("ROLE_VIEW_MANAGED_TIMES");
    rolesWithApprovalAccess.add("ROLE_VIEW_TIME_ABS_DASHBOARD");

    ROLES_THAT_MANAGE_TIME_OR_ABSENCES = Collections.unmodifiableSet(rolesWithApprovalAccess);
  }

  private HrsRolesDao rolesDao;
  private HrsUrlDao urlDao;

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
    final String approvalsDashboardUrl =
        preferences.getValue("approvalsDashboardUrl", null);
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

    if (roles.contains("ROLE_VIEW_MANAGED_TIMES")) {
      final String approveTimeUrl = getHrsUrls().get("Approve Payable Time");
      if (StringUtils.isNotBlank(approveTimeUrl)) {
        final Link approveTime = new Link();
        approveTime.setTitle("Approve time");
        approveTime.setIcon("access_time");
      } else {
        logger.error("HRS URL [Approve Payable Time] expected but not found "
            + "and so could not be offered to emplid " + emplId);
      }
    }

    if (roles.contains("ROLE_VIEW_MANAGED_ABSENCES")) {
      final String approveAbsenceUrl = getHrsUrls().get("Approve Absence");
      if (StringUtils.isNotBlank(approveAbsenceUrl)) {
        final Link approveAbsence = new Link();
        approveAbsence.setTitle("Approve absence");
        approveAbsence.setIcon("perm_contact_calendar");
        approveAbsence.setHref(approveAbsenceUrl);
        approveAbsence.setTarget("_blank");
        linkList.add(approveAbsence);
      } else {
        logger.error("HRS URL [Approve Absence] expected but not found "
            + "and so could not be offered to emplid " + emplId);
      }
    }

    if (roles.contains("ROLE_VIEW_TIME_ABS_DASHBOARD")) {
      if (StringUtils.isNotBlank(approvalsDashboardUrl)) {
        final Link approvalsDashboard = new Link();
        approvalsDashboard.setTitle("Approvals Dashboard");
        approvalsDashboard.setIcon("check_circle");
        approvalsDashboard.setHref(approvalsDashboardUrl);
        approvalsDashboard.setTarget("_blank");
        linkList.add(approvalsDashboard);
      } else {
        logger.error("Portlet preference [approvalsDashboardUrl] expected but not found "
            + "and so could not be offered to " + emplId);
      }
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
    final String approvalsDashboardUrl =
        preferences.getValue("approvalsDashboardUrl", null);

    modelMap.put("approvalsDashboardUrl", approvalsDashboardUrl);

    return "managerLinks";
  }

}
