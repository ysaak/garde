package ysaak.hera.nexus.business.service.parameter;

import ysaak.hera.nexus.data.parameter.Parameter;
import ysaak.hera.nexus.exception.validation.ValidationException;

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
