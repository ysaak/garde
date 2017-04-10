package ysaak.garde;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.Parameter;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.exception.validation.ValidationException;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.when;

/**
 * Test class for ParameterService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestParameterService {

  @MockBean
  private ParameterRepository parameterRepository;

  @Autowired
  private ParameterService service;

  @Test
  public void testCreate() throws ValidationException {
    final Parameter expectedParameter = new Parameter();
    expectedParameter.setId(1L);
    expectedParameter.setCode("TEST_CODE");
    expectedParameter.setType(ParameterType.STRING);
    expectedParameter.setValue("TEST");

    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(expectedParameter);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);


    final Parameter p = new Parameter();
    p.setCode("TEST_CODE");
    p.setType(ParameterType.STRING);
    p.setValue("TEST");

    final Parameter cParam = service.create(p);

    Assert.assertNotNull(cParam);
    Assert.assertNotNull(cParam.getId());
    Assert.assertEquals(p.getCode(), cParam.getCode());
    Assert.assertEquals(p.getType(), cParam.getType());
    Assert.assertEquals(p.getValue(), cParam.getValue());
  }

}
