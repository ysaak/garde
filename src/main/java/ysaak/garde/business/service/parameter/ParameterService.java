package ysaak.garde.business.service.parameter;

import ysaak.garde.exception.validation.ValidationException;
import ysaak.garde.data.parameter.Parameter;

import java.util.List;

public interface ParameterService {

  /**
   * Save a parameter
   * @param parameter Parameter to save
   * @return Parameter
   */
  Parameter save(Parameter parameter) throws ValidationException;


  Parameter get(String code);

  /**
   * List all the parameters
   * @return List of parameters
   */
  List<Parameter> listAll();
}
