package ysaak.hera.nexus.business.service.child;

import java.util.List;

import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.exception.validation.ValidationException;

public interface ChildService {

  /**
   * Stores a child
   * @param child Child to store
   * @return Stored child
   */
  Child save(Child child) throws ValidationException;

  Child get(long id);
  
  List<Child> listAll();
  
  boolean hasBirthDayNearby();
}
