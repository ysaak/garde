package ysaak.garde.exception.validation;

/**
 * Base class for all validation exceptions
 */
public class ValidationException extends Exception {

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
