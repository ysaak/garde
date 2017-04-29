package ysaak.garde.gui.common;

import java.time.LocalDate;

public class Context {
  public static final Context EMPTY = new Context();

  private Long childId;

  private Long longId;

  private LocalDate date;
  
  protected Context() {
  }

  public Long getChildId() {
    return childId;
  }

  public void setChildId(Long childId) {
    this.childId = childId;
  }

  public Long getLongId() {
    return longId;
  }

  public void setLongId(Long longId) {
    this.longId = longId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
