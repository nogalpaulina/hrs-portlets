package edu.wisc.hr.service.benefits;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

public class AnnualBenefitEnrollmentDatesServiceTest {

  private AnnualBenefitEnrollmentDatesService service = new AnnualBenefitEnrollmentDatesService();

  @Test
  public void testForeshadowing() {
    // foreshadowing does not begin prematurely
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2019-09-17")));

    // foreshadowing begins Wednesday Sept 18
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2019-09-18")));

    // and continues
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2019-09-22")));

    // through the day before benefit enrollment begins
    assertTrue(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2019-09-29")));

    // and stops once benefit enrollment begins
    assertFalse(service.foreshadowAnnualBenefitsEnrollment(new LocalDate("2019-09-30")));
  }

  @Test
  public void testDuringAnnualBenefitsEnrollment() {
    // annual benefits enrollment does not begin prematurely
    assertFalse(service.duringAnnualBenefitsEnrollment(new LocalDate("2019-09-29")));

    // but does begin punctually
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2019-09-30")));

    // and continues
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2019-10-05")));

    // through the last date of annual benefits enrollment
    assertTrue(service.duringAnnualBenefitsEnrollment(new LocalDate("2019-10-25")));

    // but not beyond annual benefits enrollment
    assertFalse(service.duringAnnualBenefitsEnrollment(new LocalDate("2019-10-26")));
  }

  @Test
  public void testLastDayAnnualBenefitsEnrollment() {
    // the day before the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2019-10-24")));

    // the last day is the last day
    assertTrue(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2019-10-25")));

    // the day after the last day is not the last day
    assertFalse(service.lastDayOfAnnualBenefitEnrollment(new LocalDate("2019-10-26")));
  }

  @Test
  public void testFeedbackPeriod() {
    // the last day of benefit enrollment is not the feedback period
    assertFalse(service.feedbackPeriod(new LocalDate("2019-10-25")));

    // but the day after benefit enrollment is within the feedback period
    assertTrue(service.feedbackPeriod(new LocalDate("2019-10-26")));

    // and the feedback period continues
    assertTrue(service.feedbackPeriod(new LocalDate("2019-11-12")));

    // through its last day
    assertTrue(service.feedbackPeriod(new LocalDate("2019-11-30")));

    // but not after its last day
    assertFalse(service.feedbackPeriod(new LocalDate("2019-12-01")));
  }

  @Test
  public void testCountdown() {
    // before ABE, returns -1
    assertEquals(-1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-09-29")));

    // on the antepenultimate day of ABE, there are 2 more days remaining
    assertEquals(2, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-23")));

    // on the penultimate day of ABE, there is 1 more day remaining
    assertEquals(1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-24")));

    // on the last day of ABE, there are 0 more days remaining
    assertEquals(0, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-25")));

    // after ABE, returns -1
    assertEquals(-1, service.daysRemainingInAnnualBenefitsEnrollment(new LocalDate("2019-10-26")));
  }

}
