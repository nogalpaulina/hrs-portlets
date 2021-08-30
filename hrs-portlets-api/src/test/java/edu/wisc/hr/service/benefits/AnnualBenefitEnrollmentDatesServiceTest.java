package edu.wisc.hr.service.benefits;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

public class AnnualBenefitEnrollmentDatesServiceTest {

  private AnnualBenefitEnrollmentDatesService service = new AnnualBenefitEnrollmentDatesService();

  @Test
  public void testForeshadowing() {
    assertFalse(
      "Foreshadowing Annual Benefits Enrollment should not begin prematurely.",
      service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-18")));

    assertTrue(
      "Foreshadowing Annual Benefits Enrollment should begin on September 19, 2021.",
      service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-19")));

    assertTrue(
      "Foreshadowing Annual Benefits Enrollment should continue during the foreshadowing period.",
      service.foreshadowAnnualBenefitsEnrollment(AnnualBenefitEnrollmentDatesService.DATE_DURING_ABE_FORESHADOWING));

    assertTrue(
      "Foreshadowing should continue on the day before Annual Benefits Enrollment begins.",
      service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-26")));

    assertFalse(
      "Foreshadowing should stop once Annual Benefits Enrollment begins.",
      service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2021-09-27")));
  }

  @Test
  public void testDuringAnnualBenefitsEnrollment() {
    assertFalse(
      "Annual Benefits Enrollment should not begin prematurely on the day before ABE starts.",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-09-26")));

    assertTrue(
      "Annual Benefits Enrollment should begin punctually on the first day of ABE (September 27, 2021).",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-09-27")));

    assertTrue("Annual Benefits Enrollment should continue during the permitted period.",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-05")));

    assertTrue(
      "ABE dates service should consider last date of ABE to be during ABE.",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-22")));

    assertFalse(
      "ABE dates service should consider the day after ABE ends to NOT be during ABE",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2021-10-23")));
  }

  @Test
  public void testLastDayAnnualBenefitsEnrollment() {
    assertFalse(
      "The day before the last day of Annual Benefits Enrollment should not be considered the last day.",
      service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-21")));

    assertTrue(
      "The last day of Annual Benefits Enrollment should be considered the last day.",
      service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-22")));

    assertFalse(
      "The day after the last day of Annual Benefits Enrollment should not be considered the last day.",
      service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2021-10-23")));
  }

  @Test
  public void testFeedbackPeriod() {
    assertFalse(
      "the last day of benefit enrollment should not be within the feedback period",
      service.feedbackPeriod(new LocalDate("2021-10-22")));

    assertTrue(
      "the day after benefit enrollment is within the feedback period",
      service.feedbackPeriod(new LocalDate("2021-10-23")));

    assertTrue(
      "November 12, 2021, should be considered within the feedback period.",
      service.feedbackPeriod(new LocalDate("2021-11-12")));

    assertTrue("The last day of feedback should be considered within the feedback period.",
      service.feedbackPeriod(new LocalDate("2021-11-27")));

    // but not after its last day
    assertFalse(
      "The day after the last day of the feedback period should not be considered within the feedback period.",
      service.feedbackPeriod(new LocalDate("2021-11-28")));
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
