package edu.wisc.hr.dm.benstmt;

import java.util.Comparator;

public class BenefitStatementNameComparator implements Comparator<BenefitStatement> {

@Override
public int compare(BenefitStatement o1, BenefitStatement o2) {
  String o1Type = o1.getName().substring(0,o1.getName().indexOf(" "));
  String o2Type = o2.getName().substring(0,o2.getName().indexOf(" "));
  return o1Type.compareTo(o2Type);
}

}
