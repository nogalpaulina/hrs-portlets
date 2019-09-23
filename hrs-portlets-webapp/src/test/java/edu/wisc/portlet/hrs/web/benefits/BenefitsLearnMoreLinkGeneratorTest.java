package edu.wisc.portlet.hrs.web.benefits;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


public class BenefitsLearnMoreLinkGeneratorTest {

  BenefitsLearnMoreLinkGenerator generator =
    new BenefitsLearnMoreLinkGenerator();

  @After
  public void cleanUpTime() {
    DateTimeUtils.setCurrentMillisSystem();
  }

  @Test
  public void testRolelessNonMadisonUserOutsideAbe() {

    LocalDate beforeAbe2019 = new LocalDate("2019-01-01");
    DateTimeUtils.setCurrentMillisFixed(beforeAbe2019.toDate().getTime());

    assertEquals("https://www.wisconsin.edu/ohrwd/benefits/",
      generator.learnMoreLinkFor(new HashSet<String>(), false));

  }

  @Test
  public void testRolelessMadisonUserOutsideAbe() {

    LocalDate beforeAbe2019 = new LocalDate("2019-01-01");
    DateTimeUtils.setCurrentMillisFixed(beforeAbe2019.toDate().getTime());

    assertEquals("http://benefits.wisc.edu/",
      generator.learnMoreLinkFor(new HashSet<String>(), true));

  }

  @Test
  public void testRolelessNonMadisonUserAbeForeshadowing() {

    LocalDate foreshadowingAbe2019 = new LocalDate("2019-09-25");
    DateTimeUtils.setCurrentMillisFixed(foreshadowingAbe2019.toDate().getTime());

    assertEquals("https://hr.wisc.edu/benefits/annual-benefits-enrollment/",
        generator.learnMoreLinkFor(new HashSet<String>(), false));

  }

  @Test
  public void testRolelessMadisonUserAbeForeshadowing() {

    LocalDate foreshadowingAbe2019 = new LocalDate("2019-09-25");

    DateTimeUtils.setCurrentMillisFixed(foreshadowingAbe2019.toDate().getTime());

    assertEquals("https://hr.wisc.edu/benefits/annual-benefits-enrollment/",
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
