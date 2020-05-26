package edu.wisc.hr.dm.statement.predicate;

import static org.junit.Assert.*;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.statement.NameYearUrl;
import org.junit.Test;

public class EtfAnnualStatementOfBenefitsPredicateTest {

  Predicate<NameYearUrl> etfStatementPredicate = new EtfAnnualStatementOfBenefitsPredicate();

  @Test
  public void trueForEtfAnnualStatement() {
    NameYearUrl etfStatement = new NameYearUrl();
    etfStatement.setName("ETF Annual Statement of Benefits (WRS) Issued 2020");

    assertTrue(etfStatementPredicate.apply(etfStatement));
  }

  @Test
  public void falseForBenefitEnrollmentConfirmation() {
    NameYearUrl etfStatement = new NameYearUrl();
    etfStatement.setName("2016 Benefit Enrollment Confirmation Issued Oct 30, 2015");

    assertFalse(etfStatementPredicate.apply(etfStatement));
  }

}
