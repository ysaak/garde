package ysaak.garde.gui.events.view;

import lombok.Data;
import ysaak.garde.gui.common.Context;

@Data
public class OpenFormEvent {
  private final String viewCode;
  private final Context context;
}
