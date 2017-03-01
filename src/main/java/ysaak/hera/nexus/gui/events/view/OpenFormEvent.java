package ysaak.hera.nexus.gui.events.view;

import lombok.Data;
import ysaak.hera.nexus.gui.common.Context;

@Data
public class OpenFormEvent {
  private final String viewCode;
  private final Context context;
}
