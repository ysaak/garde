package ysaak.hera.nexus.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.validation.FieldError;

import ysaak.hera.nexus.exception.DataValidationException;

public abstract class AbstractService {

  private final Validator validator;
  
  public AbstractService() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }
  
  protected <T> void validate(T bean) throws DataValidationException {
    Set<ConstraintViolation<T>> violations = validator.validate(bean);
    
    List<FieldError> errors = new ArrayList<>();
    
    for (ConstraintViolation<T> violation : violations) {
      String propertyPath = violation.getPropertyPath().toString();
      String message = violation.getMessage();
      
      errors.add(new FieldError(bean.getClass().getSimpleName(), propertyPath, message));
    }
    
    if (errors.size() > 0) {
      throw new DataValidationException(errors);
    }
  }
}
