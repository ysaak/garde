package ysaak.hera.nexus.gui.events.view;

import lombok.Data;
import ysaak.hera.nexus.gui.common.presenter.Presenter;

@Data
public class CloseFormEvent {
  private final Presenter presenter;
}
