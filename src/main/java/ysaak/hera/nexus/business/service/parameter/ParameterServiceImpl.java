package ysaak.hera.nexus.business.service.parameter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ysaak.hera.nexus.business.repository.ParameterRepository;
import ysaak.hera.nexus.data.parameter.Parameter;
import ysaak.hera.nexus.data.parameter.ParameterType;

@Service
public class ParameterServiceImpl implements ParameterService {

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
  public Parameter get(String code) {
    return parameterRepository.findByCode(code);
  }
  
}
