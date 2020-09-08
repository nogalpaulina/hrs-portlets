package edu.wisc.hr.service.benefits;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

public class AnnualBenefitEnrollmentDatesServiceTest {

  private AnnualBenefitEnrollmentDatesService service = new AnnualBenefitEnrollmentDatesService();

  @Test
  public void testForeshadowing() {
    // foreshadowing does not begin prematurely
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2020-09-15")));

    // foreshadowing begins Wednesday Sept 16
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2020-09-16")));

    // and continues
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2020-09-22")));

    // through the day before benefit enrollment begins
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2020-09-27")));

    // and stops once benefit enrollment begins
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2020-09-28")));
  }

  @Test
  public void testDuringAnnualBenefitsEnrollment() {
    // annual benefits enrollment does not begin prematurely
    assertFalse(service.duringAnnualBenefitsEnrollment(new LocalDate("2020-09-27")));

    // but does begin punctually
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2020-09-28")));

    // and continues
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2020-10-05")));

    assertTrue(
      "ABE dates service should consider last date of ABE to be during ABE.",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2020-10-23")));

    assertFalse(
      "ABE dates service should consider the day after ABE ends to NOT be during ABE",
      service.duringAnnualBenefitsEnrollment(new LocalDate("2019-10-24")));
  }

  @Test
  public void testLastDayAnnualBenefitsEnrollment() {
    // the day before the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2020-10-22")));

    // the last day is the last day
    assertTrue(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2020-10-23")));

    // the day after the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2020-10-24")));
  }

  @Test
  public void testFeedbackPeriod() {
    // the last day of benefit enrollment is not the feedback period
    assertFalse(service.feedbackPeriod(new LocalDate("2020-10-23")));

    // but the day after benefit enrollment is within the feedback period
    assertTrue(service.feedbackPeriod(new LocalDate("2020-10-24")));

    // and the feedback period continues
    assertTrue(service.feedbackPeriod(new LocalDate("2020-11-12")));

    // through its last day
    assertTrue(service.feedbackPeriod(new LocalDate("2020-11-27")));

    // but not after its last day
    assertFalse(service.feedbackPeriod(new LocalDate("2019-11-28")));
  }

  @Test
  public void testCountdown() {
    // before ABE, returns -1
    assertEquals(-1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2020-09-20")));

    // on the antepenultimate day of ABE, there are 2 more days remaining
    assertEquals(2, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-21")));

    // on the penultimate day of ABE, there is 1 more day remaining
    assertEquals(1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-22")));

    // on the last day of ABE, there are 0 more days remaining
    assertEquals(0, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-23")));

    // after ABE, returns -1
    assertEquals(-1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-24")));
  }

}
