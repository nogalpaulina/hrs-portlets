package edu.wisc.cypress.dao.taxstmt;

import edu.wisc.hr.dm.statement.NameYearUrl;
import edu.wisc.hr.service.taxstmt.TaxStatement;

/**
 * Converts from Cypress TaxStatement to NameYearUrl
 */
public class TaxStatementToNameYearUrlConverter {

  private String payrollInformationPortletFName = "earnings-statement-for-all";

  public NameYearUrl convertToNameYearUrl (TaxStatement taxStatement) {

    if (null == taxStatement) {
      throw new NullPointerException("Cannot convert null tax statement.");
    }

    NameYearUrl convertedStatement = new NameYearUrl();

    convertedStatement.setName(taxStatement.getName());
    convertedStatement.setYear(taxStatement.getYear());

    String statementUrl = "/portal/p/" + payrollInformationPortletFName +
      "/exclusive/irs_statement.pdf.resource.uP?pP_docId=" +
      taxStatement.getDocId();

    convertedStatement.setUrl(statementUrl);

    return convertedStatement;
  }

}
