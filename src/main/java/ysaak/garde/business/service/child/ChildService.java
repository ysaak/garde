package ysaak.garde.business.service.child;

import java.util.List;

import ysaak.garde.data.Child;
import ysaak.garde.exception.validation.ValidationException;

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
