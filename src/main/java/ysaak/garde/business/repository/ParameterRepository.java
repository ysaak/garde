package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.garde.business.model.parameter.Parameter;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {
  Parameter findByCode(String code);
}
