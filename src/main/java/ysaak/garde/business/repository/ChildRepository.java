package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ysaak.garde.business.model.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {

}
