package edu.wisc.hr.service.benefits;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

public class AnnualBenefitEnrollmentDatesServiceTest {

  private AnnualBenefitEnrollmentDatesService service = new AnnualBenefitEnrollmentDatesService();

  @Test
  public void testForeshadowing() {
    // foreshadowing does not begin prematurely
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-18")));

    // foreshadowing begins Sunday Sept 19 2021
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-19")));

    // and continues
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(AnnualBenefitEnrollmentDatesService.DATE_DURING_ABE_FORESHADOWING));

    // through the day before benefit enrollment begins
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-26")));

    // and stops once benefit enrollment begins
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-27")));
  }

  @Test
  public void testDuringAnnualBenefitsEnrollment() {
    // annual benefits enrollment does not begin prematurely
    assertFalse(service.duringAnnualBenefitsEnrollment(new LocalDate("2021-09-26")));

    // but does begin punctually
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2021-09-27")));

    // and continues
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-05")));

    assertTrue(
      "ABE dates service should consider last date of ABE to be during ABE.",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-22")));

    assertFalse(
      "ABE dates service should consider the day after ABE ends to NOT be during ABE",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-23")));
  }

  @Test
  public void testLastDayAnnualBenefitsEnrollment() {
    // the day before the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-21")));

    // the last day is the last day
    assertTrue(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-22")));

    // the day after the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-23")));
  }

  @Test
  public void testFeedbackPeriod() {
    // the last day of benefit enrollment is not the feedback period
    assertFalse(service.feedbackPeriod(new LocalDate("2021-10-22")));

    // but the day after benefit enrollment is within the feedback period
    assertTrue(service.feedbackPeriod(new LocalDate("2021-10-23")));

    // and the feedback period continues
    assertTrue(service.feedbackPeriod(new LocalDate("2021-11-12")));

    // through its last day
    assertTrue(service.feedbackPeriod(new LocalDate("2021-11-27")));

    // but not after its last day
    assertFalse(service.feedbackPeriod(new LocalDate("2021-11-28")));
  }

  @Test
  public void testCountdown() {
    assertEquals(
      "before ABE, daysRemaining...() returns -1",
      -1, service.daysRemainingInAnnualBenefitsEnrollment(
        new LocalDate("2021-09-20")));

    assertEquals(
      "On the antepenultimate day of ABE, there are 2 more days remaining.",
      2,
      service.daysRemainingInAnnualBenefitsEnrollment(
        new LocalDate("2021-10-20")));

    assertEquals(
      "On the penultimate day of ABE, there is 1 more day remaining.",
      1,
      service.daysRemainingInAnnualBenefitsEnrollment(
        new LocalDate("2021-10-21")));

    assertEquals(
      "On the last day of ABE, there are 0 more days remaining.",
      0,
      service.daysRemainingInAnnualBenefitsEnrollment(
        new LocalDate("2021-10-22")));

    assertEquals(
      "After ABE, daysRemaining...() returns -1.",
      -1,
      service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2021-10-24")));
  }

}
