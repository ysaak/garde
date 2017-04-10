package ysaak.garde.gui.common;

import java.time.LocalDate;

/**
 * Created by ysaak on 22/02/2017.
 */
public class ContextBuilder {

  private final Context context;

  private ContextBuilder() {
    context = new Context();
  }

  public static ContextBuilder get() {
    return new ContextBuilder();
  }

  public static Context ofChild(Long childId) {
    return get().withChildId(childId).build();
  }

  public ContextBuilder withChildId(Long id) {
    context.setChildId(id);
    return this;
  }

  public ContextBuilder withId(Long id) {
    context.setLongId(id);
    return this;
  }

  public ContextBuilder withDate(LocalDate date) {
    context.setDate(date);
    return this;
  }

  public Context build() {
    return context;
  }
}
