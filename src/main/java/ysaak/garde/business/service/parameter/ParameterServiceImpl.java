package ysaak.garde.business.service.parameter;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.data.parameter.Parameter;
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
  public Parameter save(Parameter parameter) throws ValidationException {
    validate(parameter);
    return parameterRepository.save(parameter);
  }

  @Override
  public Parameter get(String code) {
    return parameterRepository.findByCode(code);
  }

  @Override
  public List<Parameter> listAll() {
    Iterable<Parameter> params = parameterRepository.findAll();
    if (params == null) {
      return Lists.newArrayList();
    }
    return Lists.newArrayList(params);
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
