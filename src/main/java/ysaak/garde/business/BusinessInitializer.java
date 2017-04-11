package ysaak.garde.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.Parameter;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.exception.validation.ValidationException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessInitializer {
  
  @Autowired
  private ParameterService parameterService;


  public void initialize() throws ValidationException {

    // Initialize parameters
    final List<Parameter> allParameters = parameterService.listAll();
    final List<String> existingParameters = allParameters.stream().map(parameter -> parameter.getCode()).collect(Collectors.toList());
    final List<Parameter> paramsToCreate = Collections.singletonList(
            new Parameter("BIRTH-DAY-THRESHOLD", ParameterType.INTEGER, "4")
    );

    for (Parameter param : paramsToCreate) {
      if (!existingParameters.contains(param.getCode())) {
        parameterService.save(param);
      }
    }
  }
}
