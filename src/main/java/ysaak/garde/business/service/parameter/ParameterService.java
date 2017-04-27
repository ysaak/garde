package ysaak.garde.business.service.parameter;

import ysaak.garde.exception.validation.ValidationException;
import ysaak.garde.data.parameter.ParameterDTO;

import java.util.List;

public interface ParameterService {

  /**
   * Save a parameter
   * @param parameter Parameter to save
   * @return Parameter
   */
  ParameterDTO save(ParameterDTO parameter) throws ValidationException;


  ParameterDTO get(String code);

  /**
   * List all the parameters
   * @return List of parameters
   */
  List<ParameterDTO> listAll();
}
