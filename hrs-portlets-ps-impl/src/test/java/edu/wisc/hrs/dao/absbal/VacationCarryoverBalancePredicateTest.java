package edu.wisc.hrs.dao.absbal;

import edu.wisc.hr.dm.absbal.AbsenceBalance;
import edu.wisc.hr.dm.person.Job;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Unit tests for VacationCarryoverBalancePredicate.
 */
public class VacationCarryoverBalancePredicateTest {

    private final VacationCarryoverBalancePredicate predicate = new VacationCarryoverBalancePredicate();

    /**
     * Test that predicate matches vacation carryover balances.
     */
    @Test
    public void matchesVacationCarryoverBalances() {

        final AbsenceBalance vacationCarryover = new AbsenceBalance();
        vacationCarryover.setJob(new Job());
        vacationCarryover.setEntitlement("Vacation Carryover Balance");
        vacationCarryover.setBalance(BigDecimal.ZERO);

        assertTrue(predicate.apply(vacationCarryover));

    }

    /**
     * Test that predicate does not match other kinds of vacation carryover balances.
     */
    @Test
    public void doesNotMatchOtherBalances() {

        final AbsenceBalance notVacationCarryover = new AbsenceBalance();
        notVacationCarryover.setJob(new Job());
        notVacationCarryover.setEntitlement("Not Vacation Carryover Balance");
        notVacationCarryover.setBalance(BigDecimal.ZERO);

        assertFalse(predicate.apply(notVacationCarryover));

        notVacationCarryover.setEntitlement("Vacation Allocation Balance");

        assertFalse(predicate.apply(notVacationCarryover));

        notVacationCarryover.setEntitlement("Vacation Available");

        assertFalse(predicate.apply(notVacationCarryover));

        notVacationCarryover.setEntitlement("Some other entitlement");

        assertFalse(predicate.apply(notVacationCarryover));

    }

    /**
     * Test that predicate does not match null.
     */
    @Test
    public void doesNotMatchNull() {

        assertFalse(predicate.apply(null));

    }


}