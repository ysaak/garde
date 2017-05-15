package ysaak.garde.gui.common.validation.number;

/**
 * Maximum value predicate
 */
public class MaxValuePredicate extends AbstractNumberPredicate {

  private Number maxValue;

  public MaxValuePredicate(Class<? extends Number> numberType, Number maxValue) {
    super(numberType);
    this.maxValue = maxValue;
  }

  @Override
  public boolean test(String text) {
    Number number = createInstanceOf(text);
    return (number != null) && compareValue(maxValue, number) >= 0;
  }
}
