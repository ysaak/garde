package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.garde.business.model.contract.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

}
