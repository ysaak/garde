package ysaak.garde.gui.common.components.tablecell;

import java.time.Duration;

import javafx.scene.control.TableCell;
import ysaak.garde.gui.common.Formatters;

public class DurationTableCell<S> extends TableCell<S, Duration> {
  
  @Override
  protected void updateItem(Duration item, boolean empty) {
    super.updateItem(item, empty);
    
    if (!empty) {
      setText(Formatters.formatDuration(item));
    }
    else {
      setText(null);
    }
  }
}
