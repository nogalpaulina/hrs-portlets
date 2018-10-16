package edu.wisc.hr.dm.ernstmt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Sorts EarningStatement Java beans by date paid.
 * N.B. While the domain object here is called EarningStatement, the term is consistently
 * "earnings statement" or "Earnings Statement" in employee-facing content on Service Center
 * website.
 */
public class EarningStatementDateComparator
  implements Comparator<EarningStatement> {

  private SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);

  @Override
  public int compare(final EarningStatement earningStatement1, final EarningStatement earningStatement2) {
    if (null == earningStatement1 || null == earningStatement2) {
      throw new NullPointerException("Cannot compare null EarningStatement objects.");
    }

    try {
      // extract dates
      Date earningStatement1Date = parsePaidDate(earningStatement1);
      Date earningStatement2Date = parsePaidDate(earningStatement2);
    } catch (ParseException exception) {
      throw new RuntimeException(
          "Cannot compare earning statement dates when date parsing fails", exception);
    }


    return earningStatement1Date.compareTo(earningStatement2Date);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof EarningStatementDateComparator;
  }

  private Date parsePaidDate(EarningStatement statement)
    throws ParseException {

    if (null == statement) {
      throw new NullPointerException("Cannot parse paid date from null statement");
    }

    if (null == statement.getPaid()) {
      throw new NullPointerException("Cannot parse paid date from statement with null paid date.");
    }

    return dateParser.parse(statement.getPaid());
  }

}
