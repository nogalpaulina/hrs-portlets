package edu.wisc.hr.dm.statement;

/**
 * Simple JavaBean. It's got a String name, an int year, and a String URL.
 */
public class NameYearUrl {

  private String name;
  private int year;
  private String url;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
