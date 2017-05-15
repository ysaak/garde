package ysaak.garde.business.service.contract;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.model.Child;
import ysaak.garde.business.model.contract.Contract;
import ysaak.garde.business.model.contract.ContractStatus;
import ysaak.garde.business.repository.ContractRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.exception.validation.NotUniqueValueException;
import ysaak.garde.exception.validation.ValidationException;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * Contract service implementation
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ContractServiceImpl extends AbstractService<Contract> implements ContractService {

  @Autowired
  private ContractRepository contractRepository;

  @Autowired
  private ChildService childService;

  public ContractDTO create(ContractDTO contract) throws ValidationException {

    Contract model = toModel(contract, ContractDTO.class);

    // Compute status
    computeStatus(model);

    // Validate data
    validate(model);

    // Contract is valid
    if (contract.getChild().getId() == null) {
      // Contract for a new child, create it
      final ChildDTO childDTO = childService.save(contract.getChild());
      final Child child = mappingEngine.lookup(Child.class, ChildDTO.class).convertDTO(childDTO);
      model.setChild(child);
    }

    // Save data
    model = contractRepository.save(model);

    return toDto(model, ContractDTO.class);
  }

  @Override
  public ContractDTO getActiveContract(long childId, LocalDate date) {
    Contract activeContract = contractRepository.findActiveContract(childId, date);
    activeContract = computeStatus(activeContract);
    return toDto(activeContract, ContractDTO.class);
  }

  /**
   * Compute the contract status
   * @param contract Contract to evaluate
   * @return Updated contract
   */
  private Contract computeStatus(Contract contract) {
    Preconditions.checkNotNull(contract, "Contract is null");

    ContractStatus status = ContractStatus.ESTIMATE;
    if (contract.getStartDate() != null && contract.getStartDate().isBefore(LocalDate.now())) {
      status = ContractStatus.ACTIVE;
    }
    if (contract.getEndDate() != null && contract.getEndDate().isAfter(LocalDate.now())) {
      status = ContractStatus.ENDED;
    }

    contract.setStatus(status);
    return contract;
  }

  @Override
  protected void validate(Contract bean) throws ValidationException {
    super.validate(bean);

    if (computeStatus(bean).getStatus() == ContractStatus.ACTIVE) {

      // Check is no other contracts is active
      Contract activeContract = contractRepository.findActiveContract(bean.getChild().getId(), LocalDate.now());
      if (activeContract != null) {
        throw new NotUniqueValueException("Another contract is active");
      }
    }
  }
}
