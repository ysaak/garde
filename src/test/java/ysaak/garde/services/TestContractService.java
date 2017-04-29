package ysaak.garde.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.model.contract.Contract;
import ysaak.garde.business.repository.ContractRepository;
import ysaak.garde.business.service.contract.ContractService;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.exception.validation.FieldValidationException;
import ysaak.garde.exception.validation.ValidationException;

import javax.validation.constraints.NotNull;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class from contract service implementation
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestContractService extends AbstractTestService {
  @MockBean
  private ContractRepository contractRepository;

  @Autowired
  private ContractService service;

  @Test
  public void testCreateFieldValidationFailed() throws ValidationException {
    when(contractRepository.save(any(Contract.class))).thenReturn(new Contract());
    //when(contractRepository.findByCode(anyString())).thenReturn(expectedParameter);

    final ContractDTO contract = new ContractDTO();

    // Check not null validation
    try {
      service.create(contract);
      Assert.fail("Values not detected as empty");
    }
    catch (FieldValidationException e) {
      assertListContains(e.getInvalidField(), findAnnotatedFieldNames(Contract.class, NotNull.class));
    }
  }
}
