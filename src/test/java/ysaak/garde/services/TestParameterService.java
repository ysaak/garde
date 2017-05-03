package ysaak.garde.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.MappingConfiguration;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.repository.ParameterRepository;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.ValidationException;

import javax.validation.constraints.NotNull;
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
@Import(MappingConfiguration.class)
public class TestParameterService extends AbstractTestService {

  @MockBean
  private ParameterRepository parameterRepository;

  @Autowired
  private ParameterService service;

  @Test
  public void testCreate() throws ValidationException {
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
    Assert.assertEquals(p.getCode(), cParam.getCode());
    Assert.assertEquals(p.getType(), cParam.getType());
    Assert.assertEquals(p.getValue(), cParam.getValue());
  }

  @Test
  public void testUpdateSuccess() throws ValidationException {
    final Parameter existingParameter = new Parameter();
    existingParameter.setId(1L);
    existingParameter.setCode("TEST_CODE");
    existingParameter.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    existingParameter.setValue("TEST");

    final Parameter updatedParameter = new Parameter();
    updatedParameter.setId(1L);
    updatedParameter.setCode("TEST_CODE");
    updatedParameter.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    updatedParameter.setValue("TEST NEW STRING");

    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(updatedParameter);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(existingParameter);

    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST_CODE");
    p.setType(ParameterType.STRING);
    p.setValue("TEST NEW STRING");

    final ParameterDTO cParam = service.save(p);

    Assert.assertNotNull(cParam);
    Assert.assertEquals(p.getCode(), cParam.getCode());
    Assert.assertEquals(p.getType(), cParam.getType());
    Assert.assertEquals(p.getValue(), cParam.getValue());
  }

  @Test
  public void testUpdateListSuccess() throws ValidationException {
    final Parameter existingParameter1 = new Parameter();
    existingParameter1.setId(1L);
    existingParameter1.setCode("TEST_CODE_1");
    existingParameter1.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    existingParameter1.setValue("TEST");

    final Parameter existingParameter2 = new Parameter();
    existingParameter2.setId(1L);
    existingParameter2.setCode("TEST_CODE_2");
    existingParameter2.setType(ysaak.garde.business.model.parameter.ParameterType.STRING);
    existingParameter2.setValue("TEST");

    when(this.parameterRepository.findByCode("TEST_CODE_1")).thenReturn(existingParameter1);
    when(this.parameterRepository.findByCode("TEST_CODE_2")).thenReturn(existingParameter2);

    when(this.parameterRepository.save(any(Parameter.class))).then(invocation -> invocation.getArguments()[0]);

    final ParameterDTO p1 = new ParameterDTO();
    p1.setCode("TEST_CODE_1");
    p1.setType(ParameterType.STRING);
    p1.setValue("TEST NEW STRING");

    final ParameterDTO p2 = new ParameterDTO();
    p2.setCode("TEST_CODE_2");
    p2.setType(ParameterType.STRING);
    p2.setValue("TEST NEW STRING");

    List<ParameterDTO> request = Arrays.asList(p1, p2);

    final List<ParameterDTO> paramList = service.save(request);
    Assert.assertNotNull(paramList);
    Assert.assertEquals(request.size(), paramList.size());

    for (int i = 0; i<request.size(); i++) {

      ParameterDTO p = request.get(i);
      ParameterDTO cParam = paramList.get(i);

      Assert.assertNotNull(cParam);
      Assert.assertEquals(p.getCode(), cParam.getCode());
      Assert.assertEquals(p.getType(), cParam.getType());
      Assert.assertEquals(p.getValue(), cParam.getValue());
    }
  }

  @Test
  public void testFieldValidation() throws ValidationException {
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
      assertListContains(e.getInvalidField(), findAnnotatedFieldNames(Parameter.class, NotNull.class));
    }
  }

  @Test
  public void testCreateValueIntTypeValidationSuccess() throws ValidationException {
    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(null);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);

    Integer[] validIntegerValues = new Integer[] {
            -1, 100, 98732, 12, +42
    };

    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST");
    p.setType(ParameterType.INTEGER);

    for (Integer i : validIntegerValues) {
      p.setValue(i.toString());
      service.save(p);
    }
  }

  @Test(expected = ValidationException.class)
  public void testCreateValueIntTypeValidationFailure() throws ValidationException {
    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(null);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);

    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST");
    p.setType(ParameterType.INTEGER);
    p.setValue("not an integer");

    service.save(p);
  }

  @Test
  public void testCreateValueDoubleTypeValidationSuccess() throws ValidationException {
    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(null);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);

    Double[] validDoubleValues = new Double[] {
            -1., 12.012, +42d
    };

    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST");
    p.setType(ParameterType.DOUBLE);

    for (Double d : validDoubleValues) {
      p.setValue(d.toString());
      service.save(p);
    }
  }

  @Test(expected = ValidationException.class)
  public void testCreateValueDoubleTypeValidationFailure() throws ValidationException {
    when(this.parameterRepository.save(any(Parameter.class))).thenReturn(null);
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);

    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST");
    p.setType(ParameterType.DOUBLE);
    p.setValue("not an integer");

    service.save(p);
  }

  @Test(expected = ValidationException.class)
  public void testUpdateValidationFailure() throws ValidationException {
    final Parameter existingParameter = new Parameter();
    existingParameter.setId(1L);
    existingParameter.setCode("TEST_CODE");
    existingParameter.setType(ysaak.garde.business.model.parameter.ParameterType.INTEGER);
    existingParameter.setValue("1");

    when(this.parameterRepository.findByCode(anyString())).thenReturn(existingParameter);


    final ParameterDTO p = new ParameterDTO();
    p.setCode("TEST_CODE");
    p.setType(ParameterType.STRING);
    p.setValue("TEST NEW STRING");

    service.save(p);
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

    when(this.parameterRepository.findAll()).thenReturn(mapper.lookup(Parameter.class, ParameterDTO.class).convertDTO(expectedResult));

    final List<ParameterDTO> result2 = service.listAll();
    Assert.assertNotNull(result2);
    Assert.assertEquals(2, result2.size());

    assertListContains(result2, expectedResult.get(0), expectedResult.get(1));
  }
}
