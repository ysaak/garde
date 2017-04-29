package ysaak.garde.gui.events.view;

import ysaak.garde.gui.common.Context;

public class OpenFormEvent {
  private final String viewCode;
  private final Context context;

  public OpenFormEvent(String viewCode, Context context) {
    this.viewCode = viewCode;
    this.context = context;
  }

  public String getViewCode() {
    return viewCode;
  }

  public Context getContext() {
    return context;
  }
}
