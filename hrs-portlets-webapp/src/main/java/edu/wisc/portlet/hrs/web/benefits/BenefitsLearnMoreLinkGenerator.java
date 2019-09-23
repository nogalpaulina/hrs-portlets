package edu.wisc.portlet.hrs.web.benefits;

import edu.wisc.hr.service.benefits.AnnualBenefitEnrollmentDatesService;
import java.util.Set;
import org.joda.time.LocalDate;

public class BenefitsLearnMoreLinkGenerator {

  private AnnualBenefitEnrollmentDatesService datesService =
      new AnnualBenefitEnrollmentDatesService();

  public String learnMoreLinkFor(Set<String> roles, boolean madisonness) {


    // employees with a personal event-driven enrollment opportunity
    // get a learn more link about that new hire opportunity

    if (roles.contains("ROLE_VIEW_NEW_HIRE_BENEFITS")) {
      if (madisonness) {
        return "https://hr.wisc.edu/benefits/new-employee-benefits-enrollment/";
      } else {
        return "https://www.wisconsin.edu/ohrwd/benefits/";
      }
    }

    // employees with an annual benefit enrollment opportunity
    // get a learn more link about that annual opportunity

    if (roles.contains("ROLE_VIEW_OPEN_ENROLL_BENEFITS")) {
      if (madisonness) {
        return "https://hr.wisc.edu/benefits/annual-benefits-enrollment/";
      } else {
        return "https://www.wisconsin.edu/abe/";
      }
    }

    // otherwise, during ABE foreshadowing,
    // everyone gets the annual benefits enrollment preview learn more link
    // because MyUW cannot tell whether the foreshadowing is relevant to the employee

    if (datesService.foreshadowAnnualBenefitsEnrollment(new LocalDate())) {
      if (madisonness) {
        return "https://hr.wisc.edu/benefits/annual-benefits-enrollment/";
      } else {
        return "https://www.wisconsin.edu/abe/";
      }
    }

    // otherwise, everyone gets their routine benefits information URL

    if (madisonness) {
      return "http://benefits.wisc.edu/";
    } else {
      return "https://www.wisconsin.edu/ohrwd/benefits/";
    }

  }

}
