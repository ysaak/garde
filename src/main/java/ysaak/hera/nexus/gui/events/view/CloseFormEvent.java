package ysaak.hera.nexus.gui.events.view;

import lombok.Data;
import ysaak.hera.nexus.gui.common.presenter.Presenter;

@Data
public class CloseFormEvent {
  private Presenter presenter;
  private boolean closeWholeContext = false;

  public CloseFormEvent() { /* Empty constructor */}

  public CloseFormEvent(Presenter presenter) {
    this.presenter = presenter;
  }
}
