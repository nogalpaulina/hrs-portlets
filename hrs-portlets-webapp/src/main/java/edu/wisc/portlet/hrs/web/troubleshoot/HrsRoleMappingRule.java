package edu.wisc.portlet.hrs.web.troubleshoot;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * JavaBean for representing HRS role mapping rules for convenient use in a JSP articulating the
 * rules. Model object especially suited for presentation in view.
 */
public class HrsRoleMappingRule {

  private String hrsRole;

  private Set<String> portletRoles;

  public HrsRoleMappingRule(String hrs_role, String one_portlet_role) {

    this.hrsRole = hrs_role;

    if (null != one_portlet_role) {
      this.portletRoles = new HashSet<String>(1);
      this.portletRoles.add(one_portlet_role);
    }

  }

  public HrsRoleMappingRule(String hrs_role, Set<String> portletRoles) {

    this.hrsRole = hrs_role;

    this.portletRoles = portletRoles;

  }

  /**
   * Get the String identifier of the HRS role this rule is about. This rule is a mapping from this
   * HRS role to Portlet roles. Returns (none) if HRS role not set.
   *
   * @return non-null String representing HRS role
   */
  public String getHrsRole() {

    if (null == this.hrsRole) {
      return "(none)";
    } else {
      return this.hrsRole;
    }
  }

  /**
   * Get the String representing one or more Portlet roles, as a phrase suitable for inclusion in an
   * English language sentence. Example: one role: "SOME_PORTLET_ROLE" Example: several roles:
   * "SOME_PORTLET_ROLE, SOME_OTHER_ROLE, YET_ANOTHER_ROLE"
   */
  public String getPortletRolePhrase() {

    if (null == this.portletRoles || this.portletRoles.isEmpty()) {
      return "(none)";
    }

    List<String> portletRolesList = new ArrayList(this.portletRoles);

    Collections.sort(portletRolesList);

    Iterator<String> alphabeticalPortletRoles = portletRolesList.iterator();

    String portletRolePhrase = alphabeticalPortletRoles.next();

    while(alphabeticalPortletRoles.hasNext()) {

      portletRolePhrase = portletRolePhrase + ", " + alphabeticalPortletRoles.next();
    }

    return portletRolePhrase;
  }

  /**
   * True if the portletRolePhrase represents multiple roles, false otherwise.
   */
  public boolean isMultiplePortletRoles() {

    // if portlet roles not set, or if it contains less than two items
    // it's not multiple.
    return !((null == this.portletRoles) || this.portletRoles.size() < 2);
  }



}
