package edu.wisc.portlet.hrs.web.listoflinks;

/**
 * Represents a link suitable for inclusion in a JSON array to feed the list-of-links widget type.
 */
public class Link {

  private String title;
  private String href;
  private String target;
  private String icon;


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
