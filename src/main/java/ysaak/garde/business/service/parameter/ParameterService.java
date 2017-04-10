package ysaak.garde.business.service.parameter;

import ysaak.garde.exception.validation.ValidationException;
import ysaak.garde.data.parameter.Parameter;

public interface ParameterService {

  /**
   * Create a parameter
   * @param parameter Parameter to create
   * @return Parameter
   */
  Parameter create(Parameter parameter) throws ValidationException;

  /**
   * Update a parameter
   * @param parameter Parameter to update
   * @return Updated parameter
   */
  Parameter update(Parameter parameter);

  Parameter get(String code);
}
