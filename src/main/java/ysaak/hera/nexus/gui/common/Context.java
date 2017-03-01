package ysaak.hera.nexus.gui.common;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Context {
  public static final Context EMPTY = new Context();

  private Long longId;

  private LocalDate date;
  
  protected Context() {
  }
}
