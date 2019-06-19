package edu.wisc.portlet.hrs.web.benefits;

import java.util.Set;

public class BenefitsLearnMoreLinkGenerator {

  public String learnMoreLinkFor(Set<String> roles, boolean madisonness) {

    if (!madisonness) {
      // regardless of situation, non-madison employees get this learn more link
      return "https://www.wisconsin.edu/ohrwd/benefits/";
    } else if (roles.contains("ROLE_VIEW_NEW_HIRE_BENEFITS")) {
      // madison users with a new hire event get this learn more link
      return "https://hr.wisc.edu/benefits/new-employee-benefits-enrollment/";
    } else if (roles.contains("ROLE_VIEW_OPEN_ENROLL_BENEFITS")) {
      // madison users with an annual benefit enrollment opportunity get this
      // learn more link
      return "https://hr.wisc.edu/benefits/annual-benefits-enrollment/";
    } else {
      // madison users without an enrollment opportunity get this learn more
      // link
      return "http://benefits.wisc.edu/";
    }

  }

}
