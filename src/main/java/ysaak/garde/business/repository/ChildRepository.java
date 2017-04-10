package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ysaak.garde.data.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {

}
