package edu.wisc.hrs.dao.absbal;

import edu.wisc.hr.dm.absbal.AbsenceBalance;
import edu.wisc.hr.dm.person.Job;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DuplicateZeroVacationCarryoverBalanceFilterTest {

    private DuplicateZeroVacationCarryoverBalanceFilter filter = new DuplicateZeroVacationCarryoverBalanceFilter();

    /**
     * Test that removes extra zero balance vacation carryover, retaining just one,
     * given list with two zero balance Vacation Carryover Balance s for the same job.
     */
    @Test
    public void removesExtraZeroBalanceVacationCarryoversForASingleJob() {

        final Job someJob = new Job();
        someJob.setId(1);
        someJob.setTitle("ScrumMaster 3");

        final AbsenceBalance aZeroBalanceVacationCarryover = new AbsenceBalance();
        aZeroBalanceVacationCarryover.setBalance(BigDecimal.ZERO);
        aZeroBalanceVacationCarryover.setEntitlement("Vacation Carryover Balance");
        aZeroBalanceVacationCarryover.setJob(someJob);


        final AbsenceBalance anotherZeroBalanceVacationCarryover = new AbsenceBalance();
        anotherZeroBalanceVacationCarryover.setBalance(BigDecimal.ZERO);
        anotherZeroBalanceVacationCarryover.setEntitlement("Vacation Carryover Balance");
        anotherZeroBalanceVacationCarryover.setJob(someJob);

        final List<AbsenceBalance> beforeFiltering = new ArrayList<AbsenceBalance>();
        beforeFiltering.add(aZeroBalanceVacationCarryover);
        beforeFiltering.add(anotherZeroBalanceVacationCarryover);

        final List<AbsenceBalance> afterFiltering = filter.filter(beforeFiltering);

        final List<AbsenceBalance> expected = new ArrayList<AbsenceBalance>();
        // expect that filter will retain the first zero balance and
        // drop the second one.
        expected.add(aZeroBalanceVacationCarryover);

        assertEquals(expected, afterFiltering);

    }

    /**
     * Test that removes extra zero balance vacation carryover, retaining just one,
     * for each of two jobs,
     * given list with two zero balance Vacation Carryover Balance s for each job.
     */
    @Test
    public void removesExtraZeroBalanceVacationCarryoversForMultipleJob() {

        final Job scrumMaster = new Job();
        scrumMaster.setId(1);
        scrumMaster.setTitle("ScrumMaster 3");

        final AbsenceBalance scrumMasterZeroBalVacationCarryover = new AbsenceBalance();
        scrumMasterZeroBalVacationCarryover.setBalance(BigDecimal.ZERO);
        scrumMasterZeroBalVacationCarryover.setEntitlement("Vacation Carryover Balance");
        scrumMasterZeroBalVacationCarryover.setJob(scrumMaster);


        final AbsenceBalance anotherScrumMasterZeroBalVacationCarryover = new AbsenceBalance();
        anotherScrumMasterZeroBalVacationCarryover.setBalance(BigDecimal.ZERO);
        anotherScrumMasterZeroBalVacationCarryover.setEntitlement("Vacation Carryover Balance");
        anotherScrumMasterZeroBalVacationCarryover.setJob(scrumMaster);


        final Job productManager = new Job();
        scrumMaster.setId(2);
        scrumMaster.setTitle("Product Manager");

        final AbsenceBalance productManagerZeroBalVacationCarryover = new AbsenceBalance();
        productManagerZeroBalVacationCarryover.setBalance(BigDecimal.ZERO);
        productManagerZeroBalVacationCarryover.setEntitlement("Vacation Carryover Balance");
        productManagerZeroBalVacationCarryover.setJob(productManager);


        final AbsenceBalance anotherProductManagerZeroBalVacationCarryover = new AbsenceBalance();
        anotherProductManagerZeroBalVacationCarryover.setBalance(BigDecimal.ZERO);
        anotherProductManagerZeroBalVacationCarryover.setEntitlement("Vacation Carryover Balance");
        anotherProductManagerZeroBalVacationCarryover.setJob(productManager);

        final List<AbsenceBalance> beforeFiltering = new ArrayList<AbsenceBalance>();
        beforeFiltering.add(scrumMasterZeroBalVacationCarryover);
        beforeFiltering.add(anotherScrumMasterZeroBalVacationCarryover);
        beforeFiltering.add(productManagerZeroBalVacationCarryover);
        beforeFiltering.add(anotherProductManagerZeroBalVacationCarryover);

        final List<AbsenceBalance> afterFiltering = filter.filter(beforeFiltering);

        final List<AbsenceBalance> expected = new ArrayList<AbsenceBalance>();
        // expect that filter will retain the first zero balance and
        // drop the second one, for each job.
        expected.add(scrumMasterZeroBalVacationCarryover);
        expected.add(productManagerZeroBalVacationCarryover);

        assertEquals(expected, afterFiltering);

    }


    /**
     * Test that does not remove non-zero Vacation Carryover Balance entitlements, even when there are multiple of them.
     */
    @Test
    public void retainsNonzeroVacationCarryoverBalances() {

        final Job someJob = new Job();
        someJob.setId(1);
        someJob.setTitle("Technical Lead");

        final AbsenceBalance aNonZeroBalanceVacationCarryover = new AbsenceBalance();
        aNonZeroBalanceVacationCarryover.setBalance(BigDecimal.ONE);
        aNonZeroBalanceVacationCarryover.setEntitlement("Vacation Carryover Balance");
        aNonZeroBalanceVacationCarryover.setJob(someJob);


        final AbsenceBalance anotherNonZeroBalanceVacationCarryover = new AbsenceBalance();
        anotherNonZeroBalanceVacationCarryover.setBalance(BigDecimal.ONE);
        anotherNonZeroBalanceVacationCarryover.setEntitlement("VacationCarryoverBalance");
        anotherNonZeroBalanceVacationCarryover.setJob(someJob);


        final List<AbsenceBalance> beforeFiltering = new ArrayList<AbsenceBalance>();
        beforeFiltering.add(aNonZeroBalanceVacationCarryover);
        beforeFiltering.add(anotherNonZeroBalanceVacationCarryover);

        final List<AbsenceBalance> afterFiltering = filter.filter(beforeFiltering);

        assertEquals(beforeFiltering, afterFiltering);

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionOnNullInput() {
        filter.filter(null);
    }

}
