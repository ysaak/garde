package ysaak.garde.gui.common.validation.number;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

/**
 * Abstract number predicate which include utility methods
 */
abstract class AbstractNumberPredicate implements Predicate<String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNumberPredicate.class);

  private final Class<? extends Number> numberType;

  AbstractNumberPredicate(Class<? extends Number> numberType) {
    this.numberType = numberType;
  }

  protected Number createInstanceOf(String text) {
    Number value = null;
    if (text != null && !text.trim().isEmpty()) {
      try {
        Constructor constructor = numberType.getConstructor(String.class);
        value = (Number) constructor.newInstance(text);
      } catch (Exception e) {

        // Silently ignore NumberFormatException
        if (!(e instanceof InvocationTargetException && e.getCause() != null && e.getCause() instanceof NumberFormatException)) {
          LOGGER.error("Error while creating instance of " + numberType.toGenericString() + " with value '" + text + "'", e);
        }
        else {
          LOGGER.trace("Error while creating instance of {} with value '{}'", numberType, text);
        }
      }
    }
    return value;
  }

  @SuppressWarnings("unchecked")
  protected int compareValue(Number number1, Number number2) {
    if (((Object) number2).getClass().equals(((Object) number1).getClass())) {
      // both numbers are instances of the same type!
      if (number1 instanceof Comparable) {
        // and they implement the Comparable interface
        return ((Comparable) number1).compareTo(number2);
      }
    }
    // for all different Number types, let's check there double values
    if (number1.doubleValue() < number2.doubleValue())
      return -1;
    if (number1.doubleValue() > number2.doubleValue())
      return 1;
    return 0;
  }
}
