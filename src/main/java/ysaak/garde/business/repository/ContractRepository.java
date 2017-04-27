package ysaak.garde.business.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.garde.business.model.contract.Contract;

import java.time.LocalDate;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

  // FIXME query fail
  @Query("FROM Contract c WHERE c.child.id = ?1 AND c.startDate <= ?2 AND (c.endDate IS NULL OR c.endDate <= ?2)")
  Contract findActiveContract(Long childId, LocalDate searchDate);

  Contract findByChildId(Long childId);
}
