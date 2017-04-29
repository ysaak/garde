package ysaak.garde.gui.events.view;

import ysaak.garde.gui.common.presenter.Presenter;

public class CloseFormEvent {
  private Presenter presenter;
  private boolean closeWholeContext = false;

  public CloseFormEvent() { /* Empty constructor */}

  public CloseFormEvent(Presenter presenter) {
    this.presenter = presenter;
  }

  public Presenter getPresenter() {
    return presenter;
  }

  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  public boolean isCloseWholeContext() {
    return closeWholeContext;
  }

  public void setCloseWholeContext(boolean closeWholeContext) {
    this.closeWholeContext = closeWholeContext;
  }
}
