package ysaak.garde.business.service.contract;

import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.exception.validation.ValidationException;

import java.time.LocalDate;

/**
 * Service managing contracts
 */
public interface ContractService {

  /**
   * Create a new contract
   * @param contract Contract to create
   * @return Created contract
   * @throws ValidationException Thrown if contract is not valid
   */
  ContractDTO create(ContractDTO contract) throws ValidationException;

  /**
   * Gets the active contract for a child at a specific date
   * @param childId ID of a child
   * @param date Search date
   * @return Contract
   */
  ContractDTO getActiveContract(long childId, LocalDate date);
}
