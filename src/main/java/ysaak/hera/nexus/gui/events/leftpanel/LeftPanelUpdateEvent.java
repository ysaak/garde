package ysaak.hera.nexus.gui.events.leftpanel;

import lombok.Data;
import ysaak.hera.nexus.gui.common.presenter.Presenter;

@Data
public class LeftPanelUpdateEvent {
  private final Long childId;
}
