package ysaak.garde.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.NotUniqueValueException;
import ysaak.garde.exception.validation.ValidationException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.when;

/**
 * Test class for ParameterService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestParameterService extends AbstractTestService {

  @MockBean
  private ParameterRepository parameterRepository;

  @Autowired
  private ParameterService service;

  @Test
  public void testSave() throws ValidationException {
    final Parameter expectedParameter = new Parameter();
    expectedParameter.setId(1L);
    expectedParameter.setCode("TEST_CODE");
    expectedParameter.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    expectedParameter.setValue("TEST");

    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(expectedParameter);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);


    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST_CODE");
    p.setType(ParameterType.STRING);
    p.setValue("TEST");

    final ParameterDTO cParam = service.save(p);

    Assert.assertNotNull(cParam);
    Assert.assertNotNull(cParam.getId());
    Assert.assertEquals(p.getCode(), cParam.getCode());
    Assert.assertEquals(p.getType(), cParam.getType());
    Assert.assertEquals(p.getValue(), cParam.getValue());
  }

  @Test
  public void testSaveValidation() throws ValidationException {
    final Parameter expectedParameter = new Parameter();
    expectedParameter.setId(1L);
    expectedParameter.setCode("TEST_CODE");
    expectedParameter.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    expectedParameter.setValue("TEST");

    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(expectedParameter);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(expectedParameter);


    final ParameterDTO p = new ParameterDTO();

    // Check not null validation
    try {
      service.save(p);
      Assert.fail("Code not detected as empty");
    }
    catch (FieldValidationException e) {
      assertListContains(e.getInvalidField(), "code", "type", "value");
    }

    // Fill parameter
    p.setCode("TEST_CODE");
    p.setType(ParameterType.STRING);
    p.setValue("TEST");

    try {
      service.save(p);
      Assert.fail("Code duplication not detected");
    }
    catch (NotUniqueValueException e) {
      // Code detected as duplicated
    }
  }

  @Test
  public void testListAll() {
    when(this.parameterRepository.findAll()).thenReturn(null);

    // Check empty result
    final List<ParameterDTO> result1 = service.listAll();
    Assert.assertNotNull(result1);
    Assert.assertTrue(result1.isEmpty());

    // Check not empty result
    final List<ParameterDTO> expectedResult = Arrays.asList(
            new ParameterDTO("TEST_CODE1", ParameterType.STRING, "TEST"),
            new ParameterDTO("TEST_CODE2", ParameterType.STRING, "TEST")
    );

    //when(this.parameterRepository.findAll()).thenReturn(mapper.map(expectedResult, Parameter.class));

    final List<ParameterDTO> result2 = service.listAll();
    Assert.assertNotNull(result2);
    Assert.assertEquals(2, result2.size());

    assertListContains(result2, expectedResult.get(0), expectedResult.get(1));
  }
}
