package edu.wisc.portlet.hrs.web.troubleshoot;

import java.util.Set;

/**
 * JavaBean for representing HRS role mapping rules for convenient use in a JSP articulating the
 * rules. Model object especially suited for presentation in view.
 */
public class HrsRoleMappingRule {

  private String hrsRole;

  private String portletRolePhrase;

  /**
   * Get the String identifier of the HRS role this rule is about.
   * This rule is a mapping from this HRS role to Portlet roles.
   * @return String representing HRS role
   */
  public String getHrsRole() {
    return hrsRole;
  }

  public void setHrsRole(String hrsRole) {
    this.hrsRole = hrsRole;
  }

  /**
   * Get the String representing one or more Portlet roles, as a phrase suitable for inclusion
   * in an English language sentence.
   * Example: one role: "SOME_PORTLET_ROLE"
   * Example: several roles: "SOME_PORTLET_ROLE, SOME_OTHER_ROLE, YET_ANOTHER_ROLE"
   * @return
   */
  public String getPortletRolePhrase() {
    return portletRolePhrase;
  }

  public void setPortletRolePhrase(String portletRolePhrase) {
    this.portletRolePhrase = portletRolePhrase;
  }

  /**
   * True if the portletRolePhrase represents multiple roles, false otherwise.
   * @return
   */
  public boolean isPluralPortletRoles() {
    return pluralPortletRoles;
  }

  public void setPluralPortletRoles(boolean pluralPortletRoles) {
    this.pluralPortletRoles = pluralPortletRoles;
  }

  private boolean pluralPortletRoles;



}
