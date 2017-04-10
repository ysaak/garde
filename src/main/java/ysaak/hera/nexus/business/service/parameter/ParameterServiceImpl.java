package ysaak.hera.nexus.business.service.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.hera.nexus.business.repository.ParameterRepository;
import ysaak.hera.nexus.business.service.AbstractService;
import ysaak.hera.nexus.data.parameter.Parameter;
import ysaak.hera.nexus.data.parameter.ParameterType;
import ysaak.hera.nexus.exception.validation.NotUniqueValueException;
import ysaak.hera.nexus.exception.validation.ValidationException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional(rollbackOn = Exception.class)
public class ParameterServiceImpl extends AbstractService<Parameter> implements ParameterService {

  @Autowired
  private ParameterRepository parameterRepository;
  
  @PostConstruct
  public void initTest() {
    Parameter p1 = new Parameter();
    p1.setCode("BIRTH-DAY-THRESHOLD");
    p1.setType(ParameterType.INTEGER);
    p1.setValue("4");
    
    parameterRepository.save(p1);
  }

  @Override
  public Parameter create(Parameter parameter) throws ValidationException {
    validate(parameter);
    return parameterRepository.save(parameter);
  }

  @Override
  public Parameter update(Parameter parameter) {
    return null;
  }

  @Override
  public Parameter get(String code) {
    return parameterRepository.findByCode(code);
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
