package edu.wisc.hr.service.benefits;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Service encapsulating Annual Benefits Enrollment dates and date math.
 */
@Service
public class AnnualBenefitEnrollmentDatesService {

  public static LocalDate firstDayOfAnnualBenefitsEnrollmentForeshadowing =
      new LocalDate("2020-09-16");
  public static LocalDate firstDayOfAnnualBenefitsEnrollment =
      new LocalDate("2020-09-28");
  public static LocalDate lastDayOfAnnualBenefitsEnrollment =
      new LocalDate("2020-10-23");
  public static LocalDate lastDayOfAnnualBenefitsEnrollmentFeedback =
      new LocalDate("2020-11-27");

  /**
   * True iff on the given date should show the messages foreshadowing Annual Benefits Enrollment;
   * @param when the given date
   * @return true if during foreshadowing, false otherwise
   */
  public boolean foreshadowAnnualBenefitsEnrollment(final LocalDate when) {
    return (
        !when.isBefore(firstDayOfAnnualBenefitsEnrollmentForeshadowing)
            && when.isBefore(firstDayOfAnnualBenefitsEnrollment));
  }

  /**
   * True iff the given date is during annual benefits enrollment.
   * @param when the given date
   * @return true if during annual benefits enrollment, false otherwise
   */
  public boolean duringAnnualBenefitsEnrollment(final LocalDate when) {
    return (
        !when.isBefore(firstDayOfAnnualBenefitsEnrollment)
            && !when.isAfter(lastDayOfAnnualBenefitsEnrollment));
  }

  /**
   * Returns number of days remaining in ABE AFTER TODAY, counting the last day of ABE.
   * So, today does not count, because today might be mostly elapsed, but the last day of ABE does
   * count (if it's not today), because it's a day.
   * Returns -1 if not currently during ABE. The last day is 0 days left.
   * @param when
   * @return
   */
  public int daysRemainingInAnnualBenefitsEnrollment(final LocalDate when) {

    if (!duringAnnualBenefitsEnrollment(when)) {
      return -1;
    }

    int currentDayOfYear = when.getDayOfYear();
    int abeEndDayOfYear = lastDayOfAnnualBenefitsEnrollment.getDayOfYear();


    return abeEndDayOfYear - currentDayOfYear;
  }

  /**
   * True iff the given date is the last day of annual benefits enrollment.
   * @param when the given date
   * @return true if it's the last day
   */
  public boolean lastDayOfAnnualBenefitEnrollment(final LocalDate when) {
    return (lastDayOfAnnualBenefitsEnrollment.equals(when));
  }

  /**
   * True iff the given date is during the post-ABE feedback period.
   * @param when the given date
   * @return true if during feedback period
   */
  public boolean feedbackPeriod(final LocalDate when) {
    return (
        when.isAfter(lastDayOfAnnualBenefitsEnrollment)
            && !when.isAfter(lastDayOfAnnualBenefitsEnrollmentFeedback));
  }


}
