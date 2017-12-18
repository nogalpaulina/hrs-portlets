package edu.wisc.hrs.dao.absbal;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import edu.wisc.hr.dm.absbal.AbsenceBalance;
import edu.wisc.hr.dm.person.Job;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filter that removes extra zero-balance vacation carryover balance entitlements,
 * retaining just one zero-balance vacation carryover balance entitlement per job.
 */
public final class DuplicateZeroVacationCarryoverBalanceFilter {

    /**
     * Predicate that's true for zero-balance "Vacation Carryover Balance" absence balances.
     */
    private final Predicate<AbsenceBalance> zeroVacationCarryoverBalancePredicate =
            Predicates.and(
                    new VacationCarryoverBalancePredicate(),
                    new ZeroBalancePredicate()
            );


    /**
     * Given a List of AbsenceBalances, return a List of AbsenceBalances with the same content as the input list except
     * containing no zero-balance "Vacation Carryover Balance" entitlements other than first such zero-balance
     * "Vacation Carryover Balance" per job. That is, for any given job, an employee should have at most one
     * zero-balance "Vacation Carryover Balance", and that one should be the first such balance from the input list.
     * @param input
     * @return a filtered List<AbsenceBalance>
     * @throws IllegalArgumentException if input is null
     */
    public List<AbsenceBalance> filter(final List<AbsenceBalance> input) {

        Validate.notNull(input);

        final List<AbsenceBalance> output = new ArrayList<AbsenceBalance>();

        final Set<Job> jobsWithAlreadyIncludedZeroBalanceVacationBalances = new HashSet<Job>();

        for (final AbsenceBalance entitlement : input) {

            if (zeroVacationCarryoverBalancePredicate.apply(entitlement)) {
                // if it's the first zero-balance vacation carryover balance entitlement
                // for its job
                // include it in output (don't filter it out).

                final Job job = entitlement.getJob();

                if (!jobsWithAlreadyIncludedZeroBalanceVacationBalances.contains(job)) {
                    output.add(entitlement);
                }

                // but if it's not the first, it's reduplicative noise, don't retain it.

                // either way, definitely have now included at least one zero balance vacation carryover balance
                // for this job
                jobsWithAlreadyIncludedZeroBalanceVacationBalances.add(job);

            } else  {
                // it's not a zero-balance vacation carryover, retain it.

                output.add(entitlement);
            }

        }

        return output;
    }
}
