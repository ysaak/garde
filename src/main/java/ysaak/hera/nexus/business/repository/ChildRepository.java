package ysaak.hera.nexus.business.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ysaak.hera.nexus.data.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {

}
