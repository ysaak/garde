package ysaak.garde.gui.common.components.fields;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.CurrencyStringConverter;

/**
 * Monetary field
 */
public class MonetaryField extends TextField {

  private final TextFormatter<Number> formatter;

  public MonetaryField() {
    this(null);
  }

  @SuppressWarnings("unchecked")
  public MonetaryField(Double initialValue) {
    super();
    this.formatter = new TextFormatter<>(new CurrencyStringConverter(), initialValue);
    this.setTextFormatter(formatter);
  }


  public void setValue(Double value) {
    formatter.setValue(value);
  }

  public Double getValue() {
    final Number value = formatter.getValue();
    return (value != null) ? value.doubleValue() : null;
  }

  public ReadOnlyObjectProperty<Number> valueProperty() {
    return this.formatter.valueProperty();
  }

}
