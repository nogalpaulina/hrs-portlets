package edu.wisc.hr.dm.benstmt;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.statement.NameYearUrl;

/**
 * Predicate on NameYearUrl that is true where the name indicates the statement is a benefit
 * enrollment confirmation.
 */
public class BenefitEnrollmentConfirmationPredicate
  implements Predicate<BenefitStatement> {

  @Override
  public boolean apply(BenefitStatement input) {
    String name = input.getName();
    return (name != null && name.contains("Benefit Enrollment Confirmation"));
  }
}
