package edu.wisc.portlet.hrs.web.troubleshoot;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class HrsRoleMappingRuleTest {

  @Test
  public void testMapsHrsRoleToSinglePortletRole() {

    HrsRoleMappingRule rule =
        new HrsRoleMappingRule("HRS_ROLE", "ONE_PORTLET_ROLE");

    assertFalse(rule.isMultiplePortletRoles());
    assertEquals("HRS_ROLE", rule.getHrsRole());
    assertEquals("ONE_PORTLET_ROLE", rule.getPortletRolePhrase());

  }

  @Test
  public void testMapsHrsRoleToMultiplePortletRoles() {

    Set<String> portletRoles = new HashSet<String>();
    portletRoles.add("SOME_PORTLET_ROLE");
    portletRoles.add("SOME_OTHER_PORTLET_ROLE");
    HrsRoleMappingRule rule = new HrsRoleMappingRule("HRS_ROLE", portletRoles);

    assertTrue(rule.isMultiplePortletRoles());

    assertEquals("HRS_ROLE", rule.getHrsRole());

    // alphabetical order
    assertEquals("SOME_OTHER_PORTLET_ROLE, SOME_PORTLET_ROLE", rule.getPortletRolePhrase());


  }


  @Test
  public void testGracefullyHandlesNullHrsRole() {

    HrsRoleMappingRule rule =
        new HrsRoleMappingRule(null, "SOME_PORTLET_ROLE");

    assertFalse(rule.isMultiplePortletRoles());
    assertEquals("SOME_PORTLET_ROLE", rule.getPortletRolePhrase());
    assertEquals("(none)", rule.getHrsRole());

  }

  @Test
  public void testGracefullyHandlesNullPortletRoleString() {

    String nullString = null;
    HrsRoleMappingRule rule = new HrsRoleMappingRule("SOME_HRS_ROLE", nullString);

    assertFalse(rule.isMultiplePortletRoles());
    assertEquals("SOME_HRS_ROLE", rule.getHrsRole());
    assertEquals("(none)", rule.getPortletRolePhrase());

  }

  @Test
  public void testGracefullyHandlesNullPortletRoleSet() {

    Set<String> nullSet = null;
    HrsRoleMappingRule rule = new HrsRoleMappingRule("SOME_HRS_ROLE", nullSet);

    assertFalse(rule.isMultiplePortletRoles());
    assertEquals("SOME_HRS_ROLE", rule.getHrsRole());
    assertEquals("(none)", rule.getPortletRolePhrase());

  }

  @Test
  public void testGracefullyHandlesEmptyPortletRoleSet() {
    Set<String> nullSet = new HashSet<String>();
    HrsRoleMappingRule rule = new HrsRoleMappingRule("SOME_HRS_ROLE", nullSet);

    assertFalse(rule.isMultiplePortletRoles());
    assertEquals("SOME_HRS_ROLE", rule.getHrsRole());
    assertEquals("(none)", rule.getPortletRolePhrase());
  }

}
