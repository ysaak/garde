package ysaak.hera.nexus.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ysaak.hera.nexus.data.parameter.Parameter;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {
  public Parameter findByCode(String code);
}
