package ysaak.hera.nexus.data;

import lombok.Data;

@Data
public class Period<T> {
  private final T start;
  private final T end;
}
