package edu.wisc.portlet.hrs.web.contactinfo;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import edu.wisc.portlet.hrs.web.listoflinks.Link;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * Exposes HRS roles as JSON suitable for rendering in a list-of-links widget.
 */
@Controller
@RequestMapping("VIEW")
public class RolesDataController
  extends HrsControllerBase {

  private HrsRolesDao rolesDao;

  public HrsRolesDao getRolesDao() {
    return rolesDao;
  }

  @Autowired
  public void setRolesDao(HrsRolesDao rolesDao) {
    this.rolesDao = rolesDao;
  }

  @ResourceMapping("roles")
  public String rolesAsListOfLinksResource(ModelMap modelMap) throws IOException {
    final String emplId = PrimaryAttributeUtils.getPrimaryId();
    final Set<String> roles = this.rolesDao.getHrsRoles(emplId);

    final List<Link> linkList = new ArrayList<Link>();

    for (final String role : roles) {
      final Link link = new Link();
      link.setTitle(role);
      link.setHref("https://wiki.doit.wisc.edu/confluence/display/MUM/HRS+Roles+and+MyUW");
      link.setTarget("_blank");
      link.setIcon("fa-user-circle");
      linkList.add(link);
    }

    modelMap.put("links", linkList.toArray());
    return "linksAttrJsonView";
  }
}
