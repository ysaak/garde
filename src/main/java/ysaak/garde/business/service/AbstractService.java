package ysaak.garde.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AbstractService<MODEL> {

  @Autowired
  private ModelMapper mapper;

  private final Validator validator;
  
  public AbstractService() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }
  
  protected void validate(MODEL bean) throws ValidationException {
    Set<ConstraintViolation<MODEL>> violations = validator.validate(bean);
    
    List<String> errors = new ArrayList<>();
    
    for (ConstraintViolation<MODEL> violation : violations) {
      String propertyPath = violation.getPropertyPath().toString();
      
      errors.add(propertyPath);
    }
    
    if (errors.size() > 0) {
      throw new FieldValidationException(errors);
    }
  }

  protected <A, B> B mapTo(A data, Class<B> toClass) {
    return mapper.map(data, toClass);
  }

  protected <A, B> List<B> mapTo(Iterator<A> data, Class<B> toClass) {
    List<B> result = new ArrayList<>();

    while (data.hasNext()) {
      result.add(mapTo(data.next(), toClass));
    }

    return result;
  }

  protected <A, B> List<B> mapTo(Iterable<A> data, Class<B> toClass) {
    return mapTo(data.iterator(), toClass);
  }
}
