package ysaak.hera.nexus.exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class DataValidationException extends RuntimeException {
  private static final long serialVersionUID = -4273907312253841335L;

  private final List<FieldError> errors;
  
  public DataValidationException(List<FieldError> errors) {
    super("Data validation exception");
    this.errors = errors;
  }
  
  public List<FieldError> getErrors() {
    return errors;
  }
}
