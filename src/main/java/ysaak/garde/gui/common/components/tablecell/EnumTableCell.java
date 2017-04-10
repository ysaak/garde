package ysaak.garde.gui.common.components.tablecell;

import javafx.scene.control.TableCell;
import ysaak.garde.service.translation.I18n;

/**
 * Enum table cell
 */
public class EnumTableCell<S, T extends Enum<T>> extends TableCell<S, T> {

  private final T emptyValue;

  public EnumTableCell() {
    this(null);
  }

  public EnumTableCell(T emptyValue) {
    this.emptyValue = emptyValue;
  }

  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);

    if (item != null && !empty && (emptyValue == null || emptyValue != item)) {
      setText(I18n.get(item));
    }
    else {
      setText("");
    }
  }
}
