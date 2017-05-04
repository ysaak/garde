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
import ysaak.garde.service.mapping.Converter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.when;
import static org.mockito.Matchers.anyCollection;

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
  public void testGetOneParameterFound() {
    final Parameter existingParameter = new Parameter();
    existingParameter.setId(1L);
    existingParameter.setCode("TEST_CODE");
    existingParameter.setType(ysaak.garde.business.model.parameter.ParameterType.INTEGER);
    existingParameter.setValue("1");

    when(this.parameterRepository.findByCode("TEST_CODE")).thenReturn(existingParameter);

    final ParameterDTO parameter = service.get("TEST_CODE");

    Assert.assertNotNull(parameter);
    Assert.assertEquals(mapper.lookup(Parameter.class, ParameterDTO.class).convertEntity(existingParameter), parameter);
  }

  @Test
  public void testGetOneParameterNotFound() {
    when(this.parameterRepository.findByCode(anyString())).thenReturn(null);
    final ParameterDTO parameter = service.get("TEST_CODE");
    Assert.assertNull(parameter);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetMultipleParametersFound() {
    final Parameter existingParameter1 = new Parameter();
    existingParameter1.setId(1L);
    existingParameter1.setCode("TEST_CODE1");
    existingParameter1.setType(ysaak.garde.business.model.parameter.ParameterType.INTEGER);
    existingParameter1.setValue("1");

    final Parameter existingParameter2 = new Parameter();
    existingParameter2.setId(2L);
    existingParameter2.setCode("TEST_CODE2");
    existingParameter2.setType(ysaak.garde.business.model.parameter.ParameterType.INTEGER);
    existingParameter2.setValue("1");

    final Parameter existingParameter3 = new Parameter();
    existingParameter3.setId(3L);
    existingParameter3.setCode("TEST_CODE3");
    existingParameter3.setType(ysaak.garde.business.model.parameter.ParameterType.INTEGER);
    existingParameter3.setValue("1");

    final Map<String, Parameter> parameterMap = new HashMap<>();
    parameterMap.put(existingParameter1.getCode(), existingParameter1);
    parameterMap.put(existingParameter2.getCode(), existingParameter2);
    parameterMap.put(existingParameter3.getCode(), existingParameter3);


    when(this.parameterRepository.findByCodeIn(anyCollection())).thenAnswer(invocation -> {
      final List<Parameter> result = new ArrayList<>();

      final Iterable<String> codes = invocation.getArgumentAt(0, Iterable.class);
      for (String code : codes) {
        if (parameterMap.containsKey(code)) {
          result.add(parameterMap.get(code));
        }
      }

      return result;
    });

    final List<ParameterDTO> parameters = service.get(Arrays.asList("TEST_CODE1", "TEST_CODE2"));
    Assert.assertNotNull(parameters);
    Assert.assertEquals(2, parameters.size());


    final Converter<Parameter, ParameterDTO> converter = mapper.lookup(Parameter.class, ParameterDTO.class);
    List<ParameterDTO> expectedResult = Arrays.asList(
        converter.convertEntity(existingParameter1),
        converter.convertEntity(existingParameter2)
    );

    assertListContains(parameters, expectedResult);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetMultipleParametersNotFound() {
    when(this.parameterRepository.findByCodeIn(anyCollection())).thenReturn(null);
    final List<ParameterDTO> parameters = service.get(Arrays.asList("TEST_CODE1", "TEST_CODE2"));
    Assert.assertNotNull(parameters);
    Assert.assertTrue(parameters.isEmpty());
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
