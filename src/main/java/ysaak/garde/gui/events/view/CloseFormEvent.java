package ysaak.garde.gui.events.view;

import lombok.Data;
import ysaak.garde.gui.common.presenter.Presenter;

@Data
public class CloseFormEvent {
  private Presenter presenter;
  private boolean closeWholeContext = false;

  public CloseFormEvent() { /* Empty constructor */}

  public CloseFormEvent(Presenter presenter) {
    this.presenter = presenter;
  }
}
