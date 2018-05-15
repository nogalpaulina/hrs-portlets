package edu.wisc.portlet.hrs.web.troubleshoot;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.portlet.hrs.web.HrsControllerBase;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

      final Set<String> rawRoles = this.rolesDao.rawHrsRolesForEmplid(queriedEmplId);
      modelMap.put("rawRoles", rawRoles);

    }

    final Map<String, Set<String>> hrsRoleMappings = this.rolesDao.getHrsRoleMappings();
    final Set<HrsRoleMappingRule> rules = new HashSet<HrsRoleMappingRule>();

    for (final String hrsRole : hrsRoleMappings.keySet()) {

      Set<String> portletRoles = hrsRoleMappings.get(hrsRole);

      HrsRoleMappingRule rule = new HrsRoleMappingRule(hrsRole, portletRoles);

      rules.add(rule);

    }

    modelMap.put("rules", rules);

    return "troubleshooting";
  }

}
