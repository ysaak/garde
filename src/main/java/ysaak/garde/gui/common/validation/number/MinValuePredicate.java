package ysaak.garde.gui.common.validation.number;

/**
 * Minimum value predicate
 */
public class MinValuePredicate extends AbstractNumberPredicate {

  private Number minValue;

  public MinValuePredicate(Class<? extends Number> numberType, Number minValue) {
    super(numberType);
    this.minValue = minValue;
  }

  @Override
  public boolean test(String text) {
    Number number = createInstanceOf(text);
    return (number != null) && compareValue(minValue, number) <= 0;
  }
}
