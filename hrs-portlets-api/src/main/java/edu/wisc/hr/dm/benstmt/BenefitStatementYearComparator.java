package edu.wisc.hr.dm.benstmt;

import java.util.Comparator;

public class BenefitStatementYearComparator implements Comparator<BenefitStatement> {

@Override
public int compare(BenefitStatement o1, BenefitStatement o2) {
  return o1.getYear().compareTo(o2.getYear());
}

}
