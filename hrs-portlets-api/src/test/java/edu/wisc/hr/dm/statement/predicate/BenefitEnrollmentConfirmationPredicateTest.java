package edu.wisc.hr.dm.statement.predicate;

import static org.junit.Assert.*;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.statement.NameYearUrl;
import org.junit.Test;

public class BenefitEnrollmentConfirmationPredicateTest {

  private Predicate<NameYearUrl> benefitEnrollmentConfirmationPredicate =
    new BenefitEnrollmentConfirmationPredicate();

  @Test
  public void testTrueForBenefitEnrollmentStatement() {

    NameYearUrl benefitEnrollmentConfirmationStatement = new NameYearUrl();
    benefitEnrollmentConfirmationStatement.setName("2016 Benefit Enrollment Confirmation Issued Oct 30, 2015");

    assertTrue(benefitEnrollmentConfirmationPredicate.apply(benefitEnrollmentConfirmationStatement));
  }

  @Test
  public void testFalseForEtfAnnualStatement() {

    NameYearUrl etfAnnualStatement = new NameYearUrl();
    etfAnnualStatement.setName("ETF Annual Statement of Benefits (WRS) Issued 2016");

    assertFalse(benefitEnrollmentConfirmationPredicate.apply(etfAnnualStatement));
  }
}



