package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.garde.business.model.parameter.Parameter;

import java.util.List;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {
  /**
   * Find a parameter from its code
   * @param code Code
   * @return Parameter
   */
  Parameter findByCode(String code);

  /**
   * Find a list of parameters from their code
   * @param codes Codes
   * @return List of parameters
   */
  List<Parameter> findByCodeIn(Iterable<String> codes);
}
