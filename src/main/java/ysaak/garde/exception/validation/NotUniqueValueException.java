package ysaak.garde.exception.validation;

/**
 * Exception used when a bean's field is not unique
 */
public class NotUniqueValueException extends ValidationException {
  private final String field;

  public NotUniqueValueException(String field) {
    super("Field '" + field + "' is not unique");
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
