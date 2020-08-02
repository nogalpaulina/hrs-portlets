package edu.wisc.hr.dm.benstmt;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

/**
 * Compares the Issued date in the name field.
 * Dates must be in the medium format format, eg "Jan 1, 2018"
 */
public class BenefitStatementIssuedComparator implements Comparator<BenefitStatement> {
  private static final int GREATER_THAN = 1;
  private static final int LESS_THAN = -1;
  private static final int NOT_FOUND = -1;
  private static final String KEYWORD = "Issued ";
  private final DateFormat dateFormatter;

  public BenefitStatementIssuedComparator() {
    dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
    dateFormatter.setLenient(false);
  }

  private Date getIssuedDate(String name) {
    Date result = null;

    int issuedStart = name.indexOf(KEYWORD);
    if (issuedStart > NOT_FOUND) {
      String dateString = name.substring(issuedStart + KEYWORD.length());
      try {
        result = dateFormatter.parse(dateString);
      } catch (ParseException e) {
        // swallow
      }
    }

    return result;
  }

  @Override
  public int compare(BenefitStatement o1, BenefitStatement o2) {
    int result = 0;

    Date leftDate = getIssuedDate(o1.getName());
    Date rightDate = getIssuedDate(o2.getName());
    if (null != leftDate && null != rightDate) {
      result = leftDate.compareTo(rightDate);
    } else if (null != leftDate) {
      result = GREATER_THAN;
    } else if (null != rightDate) {
      result = LESS_THAN;
    }

    return result;
  }
}
