package ysaak.garde.business.service.parameter;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.model.parameter.ParameterType;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.exception.validation.ValidationException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class ParameterServiceImpl extends AbstractService<Parameter> implements ParameterService {

  @Autowired
  private ParameterRepository parameterRepository;

  @Override
  public ParameterDTO save(ParameterDTO parameter) throws ValidationException {
    Preconditions.checkNotNull(parameter);

    // Transform to model and validate
    Parameter model = toModel(parameter, ParameterDTO.class);

    validate(model);

    // Fetch db data
    Parameter dbParameter = parameterRepository.findByCode(model.getCode());

    if (dbParameter == null) {
      // New parameter, store it in database
      model = parameterRepository.save(model);
    }
    else {
      // Existing parameter : update existing parameter with new value and validate new parameter
      dbParameter.setValue(model.getValue());

      validate(dbParameter);

      // Updated object is valid, store it
      model = parameterRepository.save(dbParameter);
    }

    return toDto(model, ParameterDTO.class);
  }

  @Override
  public List<ParameterDTO> save(Iterable<ParameterDTO> parameters) throws ValidationException {
    List<ParameterDTO> result = new ArrayList<>();

    for (ParameterDTO param : parameters) {
      result.add(save(param));
    }

    return result;
  }

  @Override
  public ParameterDTO get(String code) {
    return toDto(parameterRepository.findByCode(code), ParameterDTO.class);
  }

  @Override
  public List<ParameterDTO> listAll() {
    Iterable<Parameter> params = parameterRepository.findAll();
    return toDto(params, ParameterDTO.class);
  }

  @Override
  protected void validate(Parameter bean) throws ValidationException {
    // Field validation
    super.validate(bean);

    // Check if stored value correspond to designated type
    if (bean.getType() == ParameterType.INTEGER) {
      try {
        bean.getIntValue();
      }
      catch (NumberFormatException e) {
        throw new ValidationException("Stored value is not an integer");
      }
    }
    else if (bean.getType() == ParameterType.DOUBLE) {
      try {
        bean.getDoubleValue();
      }
      catch (NumberFormatException e) {
        throw new ValidationException("Stored value is not an double");
      }
    }

    // String : nothing to test
  }
}
