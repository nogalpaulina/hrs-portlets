package edu.wisc.hr.dm.statement.predicate;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.statement.NameYearUrl;

/**
 * Predicate on NameYearUrl that is true where the name indicates the statement is an ETF annual
 * statement of benefits.
 */
public class EtfAnnualStatementOfBenefitsPredicate
  implements Predicate<NameYearUrl> {

  @Override
  public boolean apply(NameYearUrl input) {
    String name = input.getName();
    return (name != null && name.contains("ETF Annual Statement of Benefits"));
  }
}
