package edu.wisc.portlet.hrs.web.benefits;

import edu.wisc.hr.dm.benstmt.BenefitStatement;
import edu.wisc.hr.dm.statement.NameYearUrl;

public class BenefitStatementToNameYearUrlConverter {

  private String benefitInformationPortletFName;

  public BenefitStatementToNameYearUrlConverter(String fname) {
    this.benefitInformationPortletFName = fname;
  }

  public NameYearUrl convert(BenefitStatement statement) {

    NameYearUrl bean = new NameYearUrl();
    bean.setName(statement.getName());
    bean.setYear(statement.getYear().intValue());

    // https://my.wisc.edu/portal/p/university-staff-benefits-statement.ctf2/exclusive/benefits.pdf.resource.uP?pP_mode=null&pP_year=2019&pP_docId=28276518
    // https://my.wisc.edu/portal/p/university-staff-benefits-statement.ctf2/exclusive/benefits.pdf.resource.uP?pP_mode=etf2&pP_year=2019&pP_docId=28453695

    String url =
      "/portal/p/" + this.benefitInformationPortletFName + "/exclusive/benefits.pdf.resource.uP" +
        "?pP_mode=" + statement.getDocType() +
        "&pP_year=" + statement.getYear() +
        "&pP_docId=" + statement.getDocId();

    bean.setUrl(url);

    return bean;
  }


}
