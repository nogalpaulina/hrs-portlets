package edu.wisc.hr.service.taxstmt;

import edu.wisc.hr.dm.statement.NameYearUrl;

import java.util.List;

public interface TaxStatementService {

  /**
   * Statements, most recent to oldest.
   * @param emplid
   * @return
   */
  List<NameYearUrl> statementsForEmplid(String emplid);

}
