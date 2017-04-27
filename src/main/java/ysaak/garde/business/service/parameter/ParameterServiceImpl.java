package ysaak.garde.business.service.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.exception.validation.NotUniqueValueException;
import ysaak.garde.exception.validation.ValidationException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = Exception.class)
public class ParameterServiceImpl extends AbstractService<Parameter> implements ParameterService {

  @Autowired
  private ParameterRepository parameterRepository;

  @Override
  public ParameterDTO save(ParameterDTO parameter) throws ValidationException {

    Parameter model = toModel(parameter, ParameterDTO.class);

    validate(model);
    model = parameterRepository.save(model);

    return toDto(model, ParameterDTO.class);
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

    // Check name is unique
    Parameter param = parameterRepository.findByCode(bean.getCode());

    if (param != null && !Objects.equals(param.getId(), bean.getId())) {
      throw new NotUniqueValueException("code");
    }
  }
}
