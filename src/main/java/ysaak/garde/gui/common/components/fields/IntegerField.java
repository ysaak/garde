package ysaak.garde.gui.common.components.fields;

import javafx.util.converter.IntegerStringConverter;

/**
 * Integer field
 */
public class IntegerField extends NumberField<Integer> {

  public IntegerField() {
    super(new IntegerStringConverter());
  }

  public IntegerField(Integer initialValue) {
    super(new IntegerStringConverter(), initialValue);
  }
}
