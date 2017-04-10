package ysaak.garde.gui.common;

public class Contexts {
  public static Context emptyContext() {
    return new Context();
  }
  
  public static Context idContext(Long id) {
    Context context = emptyContext();
    context.setLongId(id);
    return context;
  }
}
