package ysaak.garde.business.service.parameter;

import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.exception.validation.ValidationException;

import java.util.List;

public interface ParameterService {
  /**
   * Save a parameter
   * @param parameter Parameter to save
   * @return Parameter
   * @throws ValidationException Thrown if parameter is invalid
   */
  ParameterDTO save(ParameterDTO parameter) throws ValidationException;

  /**
   * Save a list of parameters
   * @param parameters Parameters to save
   * @return Stored parameters
   * @throws ValidationException Thrown if one of the parameters is invalid
   */
  List<ParameterDTO> save(Iterable<ParameterDTO> parameters) throws ValidationException;

  ParameterDTO get(String code);

  /**
   * List all the parameters
   * @return List of parameters
   */
  List<ParameterDTO> listAll();
}
