package ysaak.hera.nexus.business.service.child;

import java.util.List;

import ysaak.hera.nexus.data.Child;

public interface ChildService {

  /**
   * Stores a child
   * @param child Child to store
   * @return Stored child
   */
  Child save(Child child);

  Child get(long id);
  
  List<Child> listAll();
  
  boolean hasBirthDayNearby();
}
