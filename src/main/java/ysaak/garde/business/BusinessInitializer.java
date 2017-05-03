package ysaak.garde.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.data.parameter.Parameters;
import ysaak.garde.exception.validation.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessInitializer {
  
  @Autowired
  private ParameterService parameterService;


  public void initialize() throws ValidationException {

    // Initialize parameters
    final List<ParameterDTO> allParameters = parameterService.listAll();
    final List<String> existingParameters = allParameters.stream().map(ParameterDTO::getCode).collect(Collectors.toList());

    final List<ParameterDTO> paramsToCreate = Arrays.asList(
            new ParameterDTO("BIRTH-DAY-THRESHOLD", ParameterType.INTEGER, "4"),

            // Contract
            new ParameterDTO(Parameters.CONTRACT_BASE_HOUR_VALUE, ParameterType.DOUBLE, "2.0"),
            new ParameterDTO(Parameters.CONTRACT_INCREASED_HOUR_VALUE, ParameterType.DOUBLE, "4.0")
    );

    for (ParameterDTO param : paramsToCreate) {
      if (!existingParameters.contains(param.getCode())) {
        parameterService.save(param);
      }
    }
  }
}
