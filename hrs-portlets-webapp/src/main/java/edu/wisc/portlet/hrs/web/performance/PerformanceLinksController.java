package edu.wisc.portlet.hrs.web.performance;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.hr.dao.url.HrsUrlDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import edu.wisc.portlet.hrs.web.listoflinks.Link;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletRequest;
import org.apache.commons.lang.StringUtils;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * Exposes list-of-links JSON suitable for presenting performance management
 * links to employees (including managers). Links shown depend upon user roles.
 */
@Controller
@RequestMapping("VIEW")
public class PerformanceLinksController
  extends HrsControllerBase {

  /**
   * Name of HRS Portlets role granting access to the hyperlink into HRS
   * self-service to manage other employee (supervisees) performance.
   */
  public static String ROLE_LINK_MANAGE_PERFORMANCE =
    "ROLE_LINK_MANAGE_PERFORMANCE";

  /**
   * Name of HRS Portlets role granting access to the hyperlink into HRS
   * self-service to manage one's own performance.
   */
  public static String ROLE_LINK_SELF_PERFORMANCE =
    "ROLE_LINK_SELF_PERFORMANCE";

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
   * suitable for (in JSON representation) use as the remote source for dynamic
   * list-of-links widget in uPortal-app-framework.
   * @param modelMap
   * @return String representing view
   */
  @ResourceMapping("performanceListOfLinks")
  public String performanceListOfLinks(
    ModelMap modelMap, PortletRequest request) {

    final String emplId = PrimaryAttributeUtils.getPrimaryId();
    final Set<String> roles =
      Collections.unmodifiableSet(this.rolesDao.getHrsRoles(emplId));

    final String selfPerformanceUrl =
      this.getHrsUrls().get(HrsUrlDao.SELF_PERFORMANCE_KEY);

    final String othersPerformanceUrl =
      this.getHrsUrls().get(HrsUrlDao.OTHERS_PERFORMANCE_KEY);

    final List<Link> linkList = new ArrayList<Link>();

    if (roles.contains(ROLE_LINK_SELF_PERFORMANCE)) {
      if (StringUtils.isNotBlank(selfPerformanceUrl)) {
        final Link selfPerformance = new Link();
        selfPerformance.setTitle("Employee");
        selfPerformance.setIcon("assignment_ind");
        selfPerformance.setHref(selfPerformanceUrl);
        selfPerformance.setTarget("_blank");
        linkList.add(selfPerformance);
      } else {
        logger.warn(
          "HRS URL [" + HrsUrlDao.SELF_PERFORMANCE_KEY + "] expected" +
          " but not found and so could not be offered to " + emplId);
      }
    }

    if (roles.contains(ROLE_LINK_MANAGE_PERFORMANCE)) {
      if (StringUtils.isNotBlank(othersPerformanceUrl)) {
        final Link othersPerformance = new Link();
        othersPerformance.setTitle("Manage");
        othersPerformance.setIcon("assignment_group");
        othersPerformance.setHref(othersPerformanceUrl);
        othersPerformance.setTarget("_blank");
        linkList.add(othersPerformance);
      } else {
        logger.warn(
          "HRS URL [" + HrsUrlDao.OTHERS_PERFORMANCE_KEY + "] expected" +
          " but not found and so could not be offered to " + emplId);
      }
    }

    final Map<String, Object[]> content = new HashMap<String, Object[]>();
    content.put("links", linkList.toArray());

    modelMap.put("content", content);

    return "contentAttrJsonView";
  }


  /**
   * The intended use of this portlet controller is to service the resource URL
   * to generate list-of-links JSON to drive a widget. Users are not intended to
   * ever render the portlet directly. Nonetheless, this method handles the
   * request if they do.
   * @return oops
   */
  @RequestMapping
  public String defaultView(){
    return "oops";
  }

}
