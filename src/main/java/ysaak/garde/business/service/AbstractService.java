package ysaak.garde.business.service;

import ysaak.garde.exception.validation.ValidationException;
import ysaak.garde.exception.validation.FieldValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractService<T> {

  private final Validator validator;
  
  public AbstractService() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }
  
  protected void validate(T bean) throws ValidationException {
    Set<ConstraintViolation<T>> violations = validator.validate(bean);
    
    List<String> errors = new ArrayList<>();
    
    for (ConstraintViolation<T> violation : violations) {
      String propertyPath = violation.getPropertyPath().toString();
      
      errors.add(propertyPath);
    }
    
    if (errors.size() > 0) {
      throw new FieldValidationException(errors);
    }
  }
}
