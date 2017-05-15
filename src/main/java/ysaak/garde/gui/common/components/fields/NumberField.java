package ysaak.garde.gui.common.components.fields;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * Generic number field
 */
class NumberField<T extends Number> extends TextField {

  private final TextFormatter<T> formatter;

  NumberField(StringConverter<T> converter) {
    this(converter, null);
  }

  NumberField(StringConverter<T> converter, T initialValue) {
    super();
    this.formatter = new TextFormatter<>(converter, initialValue);
    this.setTextFormatter(formatter);
  }

  public void setValue(T value) {
    formatter.setValue(value);
  }

  public T getValue() {
    return formatter.getValue();
  }

  public ReadOnlyObjectProperty<T> valueProperty() {
    return formatter.valueProperty();
  }
}
