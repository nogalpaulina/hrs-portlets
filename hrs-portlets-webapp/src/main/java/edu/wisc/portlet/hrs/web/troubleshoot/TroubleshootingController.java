package edu.wisc.portlet.hrs.web.troubleshoot;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import edu.wisc.portlet.hrs.web.listoflinks.Link;
import java.util.List;
import java.util.Set;
import javax.portlet.PortletRequest;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("VIEW")
public class TroubleshootingController
  extends HrsControllerBase {

  private HrsRolesDao rolesDao;

  public HrsRolesDao getRolesDao() {
    return rolesDao;
  }

  @Autowired
  public void setRolesDao(HrsRolesDao rolesDao) {
    this.rolesDao = rolesDao;
  }

  @RequestMapping
  public String viewRoles(ModelMap modelMap, @RequestParam(required = false) String queriedEmplId) {

    if (null != queriedEmplId) {
      modelMap.put("queriedEmplId", queriedEmplId);
      final Set<String> roles = this.rolesDao.getHrsRoles(queriedEmplId);
      modelMap.put("roles", roles);
    }

    return "troubleshooting";
  }

}
