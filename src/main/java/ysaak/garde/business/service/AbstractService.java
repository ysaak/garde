package ysaak.garde.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.service.mapping.MappingEngine;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractService<MODEL> {

  @Autowired
  private MappingEngine mappingEngine;

  private Class<MODEL> modelClass;

  private final Validator validator;

  @SuppressWarnings("unchecked")
  public AbstractService() {
    this.modelClass = (Class<MODEL>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

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

  protected <TO> TO toDto(MODEL model, Class<TO> toClass) {
    return mappingEngine.lookup(modelClass, toClass).convertEntity(model);
  }

  protected <TO> List<TO> toDto(Iterable<? extends MODEL> models, Class<TO> toClass) {
    return mappingEngine.lookup(modelClass, toClass).convertEntity(models);
  }

  protected <TO> MODEL toModel(TO object, Class<TO> toClass) {
    return mappingEngine.lookup(toClass, modelClass).convertEntity(object);
  }

  protected <TO> List<MODEL> toModel(Iterable<? extends TO> objects, Class<TO> toClass) {
    return mappingEngine.lookup(toClass, modelClass).convertEntity(objects);
  }
}
