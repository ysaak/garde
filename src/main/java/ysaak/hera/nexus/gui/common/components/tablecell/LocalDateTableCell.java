package ysaak.hera.nexus.gui.common.components.tablecell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TableCell;

public class LocalDateTableCell<S> extends TableCell<S, LocalDate> {
  
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  
  
  @Override
  protected void updateItem(LocalDate item, boolean empty) {
    super.updateItem(item, empty);
    
    if (!empty) {
      setText(formatter.format(item));
    }
    else {
      setText(null);
    }
  }
}
