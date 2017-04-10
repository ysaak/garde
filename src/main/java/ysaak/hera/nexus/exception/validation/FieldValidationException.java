package ysaak.hera.nexus.exception.validation;

import java.util.List;

/**
 * Validation exception based on fields attribute
 */
public class FieldValidationException extends ValidationException {

  private final List<String> invalidField;

  public FieldValidationException(List<String> invalidField) {
    super("Field are invalids");
    this.invalidField = invalidField;
  }

  public List<String> getInvalidField() {
    return invalidField;
  }
}
