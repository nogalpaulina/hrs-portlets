package edu.wisc.portlet.hrs.web.contactinfo;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
  public void rolesResource(ResourceResponse response) throws IOException {
    final String emplId = PrimaryAttributeUtils.getPrimaryId();
    final Set<String> roles = this.rolesDao.getHrsRoles(emplId);

    response.setContentType("application/json;charset=UTF-8");
    final PrintWriter writer = response.getWriter();

    writer.append("[");

    for (String role : roles) {
      writer.append("{"
          + "'title':'" + role + "',"
          + "'href':'https://wiki.doit.wisc.edu/confluence/display/MUM/HRS+Roles+and+MyUW',"
          + "'icon':'fa-user-circle',"
          + "'target':'_blank'"
          + "} ");
    }

    writer.append("]");
  }
}
