package edu.wisc.hrs.dao.absbal;

import com.google.common.base.Predicate;
import edu.wisc.hr.dm.absbal.AbsenceBalance;

/**
 * True for AbsenceBalances that represent entitlements with title "Vacation Carryover Balance".
 *
 * Returns false for null input.
 */
public class VacationCarryoverBalancePredicate
    implements Predicate<AbsenceBalance> {

    @Override
    public boolean apply(AbsenceBalance input) {

        return (null != input &&
          "Vacation Carryover Balance".equals(input.getEntitlement()));
    }
}
