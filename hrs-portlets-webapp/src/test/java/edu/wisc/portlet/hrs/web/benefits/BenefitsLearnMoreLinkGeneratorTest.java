package edu.wisc.portlet.hrs.web.benefits;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;


public class BenefitsLearnMoreLinkGeneratorTest {

  BenefitsLearnMoreLinkGenerator generator =
    new BenefitsLearnMoreLinkGenerator();

  @Test
  public void testRolelessNonMadisonUser() {

    assertEquals("https://www.wisconsin.edu/ohrwd/benefits/",
      generator.learnMoreLinkFor(new HashSet<String>(), false));

  }

  @Test
  public void testRolelessMadisonUser() {

    assertEquals("http://benefits.wisc.edu/",
      generator.learnMoreLinkFor(new HashSet<String>(), true));

  }

  @Test
  public void testAnnualBenefitEnrollmentOpportunityMadisonUser() {
    Set<String> roles = new HashSet<String>();
    roles.add("ROLE_VIEW_OPEN_ENROLL_BENEFITS");

    assertEquals("https://hr.wisc.edu/benefits/annual-benefits-enrollment/",
      generator.learnMoreLinkFor(roles, true));

  }

  @Test
  public void testNewHireBenefitEnrollmentOpportunityMadisonUser() {
    Set<String> roles = new HashSet<String>();
    roles.add("ROLE_VIEW_NEW_HIRE_BENEFITS");

    assertEquals(
      "https://hr.wisc.edu/benefits/new-employee-benefits-enrollment/",
      generator.learnMoreLinkFor(roles, true));
  }

  @Test
  public void testMadisonNewHireLearnMorePreferredOverAnnualLearnMore() {
    Set<String> roles = new HashSet<String>();
    roles.add("ROLE_VIEW_NEW_HIRE_BENEFITS");
    roles.add("ROLE_VIEW_OPEN_ENROLL_BENEFITS");

    assertEquals(
      "https://hr.wisc.edu/benefits/new-employee-benefits-enrollment/",
      generator.learnMoreLinkFor(roles, true));
  }

}
