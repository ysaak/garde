package ysaak.garde.gui.common.validation;

import org.controlsfx.validation.Validator;
import ysaak.garde.gui.common.validation.number.MaxValuePredicate;
import ysaak.garde.gui.common.validation.number.MinValuePredicate;
import ysaak.garde.service.translation.I18n;

import java.util.function.Predicate;

/**
 * Validators
 */
public final class StringValidators {

  public StringValidators() throws IllegalAccessException {
    throw new IllegalAccessException();
  }

  public static Validator<String> mandatoryField() {
    return Validator.createEmptyValidator(I18n.get("errors.mandatoryField"));
  }

  public static <T extends Number> Validator<String> minValueValidator(T minValue, Class<T> numberType) {
    return Validator.createPredicateValidator(new MinValuePredicate(numberType, minValue), I18n.get("errors.number.min", minValue));
  }

  public static <T extends Number> Validator<String> maxValueValidator(T maxValue, Class<T> numberType) {
    return Validator.createPredicateValidator(new MaxValuePredicate(numberType, maxValue), I18n.get("errors.number.max", maxValue));
  }

  public static <T extends Number> Validator<String> rangedValueValidator(T minValue, T maxValue, Class<T> numberType) {
    final Predicate<String> predicate = new MinValuePredicate(numberType, minValue)
            .and(new MaxValuePredicate(numberType, maxValue));
    return Validator.createPredicateValidator(predicate, I18n.get("errors.number.range", minValue, maxValue));
  }
}
