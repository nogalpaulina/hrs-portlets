package edu.wisc.hr.dm.benstmt;

import static org.junit.Assert.*;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.benstmt.BenefitEnrollmentConfirmationPredicate;
import edu.wisc.hr.dm.benstmt.BenefitStatement;
import edu.wisc.hr.dm.statement.NameYearUrl;
import org.junit.Test;

public class BenefitEnrollmentConfirmationPredicateTest {

  private Predicate<BenefitStatement> benefitEnrollmentConfirmationPredicate =
    new BenefitEnrollmentConfirmationPredicate();

  @Test
  public void testTrueForBenefitEnrollmentStatement() {

    BenefitStatement benefitEnrollmentConfirmationStatement = new BenefitStatement();
    benefitEnrollmentConfirmationStatement.setName("2016 Benefit Enrollment Confirmation Issued Oct 30, 2015");

    assertTrue(benefitEnrollmentConfirmationPredicate.apply(benefitEnrollmentConfirmationStatement));
  }

  @Test
  public void testFalseForEtfAnnualStatement() {

    BenefitStatement etfAnnualStatement = new BenefitStatement();
    etfAnnualStatement.setName("ETF Annual Statement of Benefits (WRS) Issued 2016");

    assertFalse(benefitEnrollmentConfirmationPredicate.apply(etfAnnualStatement));
  }
}



