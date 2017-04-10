package ysaak.garde.gui.common;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Context {
  public static final Context EMPTY = new Context();

  private Long childId;

  private Long longId;

  private LocalDate date;
  
  protected Context() {
  }
}
