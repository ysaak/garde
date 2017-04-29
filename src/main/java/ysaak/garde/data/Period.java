package ysaak.garde.data;

public class Period<T> {
  private final T start;
  private final T end;

  public Period(T start, T end) {
    this.start = start;
    this.end = end;
  }

  public T getStart() {
    return start;
  }

  public T getEnd() {
    return end;
  }
}
