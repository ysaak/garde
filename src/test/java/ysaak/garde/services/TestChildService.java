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
import ysaak.garde.business.model.Child;
import ysaak.garde.business.repository.ChildRepository;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.ValidationException;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test class from contract service implementation
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Import(MappingConfiguration.class)
public class TestChildService extends AbstractTestService {

  @MockBean
  private ChildRepository childRepository;

  @Autowired
  private ChildService service;

  @Test
  public void testCreateFieldValidationFailed() throws ValidationException {
    //when(contractRepository.save(any(Contract.class))).thenReturn(new Contract());
    //when(contractRepository.findByCode(anyString())).thenReturn(expectedParameter);

    final ChildDTO child = new ChildDTO();

    // Check not null validation
    try {
      service.save(child);
      Assert.fail("Values not detected as empty");
    }
    catch (FieldValidationException e) {
      assertListContains(e.getInvalidField(), findAnnotatedFieldNames(Child.class, NotNull.class));
    }
  }

  @Test
  public void testGetSuccess() {
    Child dbChild = new Child();
    dbChild.setId(1L);
    dbChild.setLastName("LAST NAME");
    dbChild.setFirstName("FIRST NAME");

    ChildDTO expectedResult = mapper.lookup(Child.class, ChildDTO.class).convertEntity(dbChild);


    when(childRepository.findOne(1L)).thenReturn(dbChild);

    ChildDTO result = service.get(1L);
    Assert.assertNotNull(result);
    Assert.assertEquals(expectedResult, result);
  }

  @Test
  public void testGetNotFound() {
    when(childRepository.findOne(anyLong())).thenReturn(null);
    ChildDTO result = service.get(1L);
    Assert.assertNull(result);
  }

  @Test
  public void testListAllEmpty() {
    when(childRepository.findAll()).thenReturn(null);

    List<ChildDTO> results = service.listAll();
    Assert.assertNotNull(results);
    Assert.assertTrue(results.isEmpty());
  }

  @Test
  public void testListAll() {
    Child dbChild1 = new Child();
    dbChild1.setId(1L);
    dbChild1.setLastName("LAST NAME 1");
    dbChild1.setFirstName("FIRST NAME 1");

    Child dbChild2 = new Child();
    dbChild2.setId(2L);
    dbChild2.setLastName("LAST NAME 2");
    dbChild2.setFirstName("FIRST NAME 2");

    List<Child> dbChilden = Arrays.asList(dbChild1, dbChild2);
    when(childRepository.findAll()).thenReturn(dbChilden);

    List<ChildDTO> results = service.listAll();
    Assert.assertNotNull(results);
    assertListContains(results, mapper.lookup(Child.class, ChildDTO.class).convertEntity(dbChilden));
  }
}
