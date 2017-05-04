package ysaak.garde.business.service.parameter;

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

  /**
   * Find a parameter from its code
   * @param code Parameter code
   * @return Parameter
   */
  ParameterDTO get(String code);

  /**
   * Find a list of parameter from their codes
   * @param codes Parameter's codes
   * @return Parameters
   */
  List<ParameterDTO> get(Iterable<String> codes);

  /**
   * List all the parameters
   * @return List of parameters
   */
  List<ParameterDTO> listAll();
}
