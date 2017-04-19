package ysaak.garde.business.service.child;

import ysaak.garde.data.ChildDTO;
import ysaak.garde.exception.validation.ValidationException;

import java.util.List;

public interface ChildService {

  /**
   * Stores a child
   * @param child Child to store
   * @return Stored child
   */
  ChildDTO save(ChildDTO child) throws ValidationException;

  /**
   * Find a child from its ID
   * @param id ID of a child
   * @return Child
   */
  ChildDTO get(long id);

  /**
   * List all children
   * @return All children
   */
  List<ChildDTO> listAll();
  
  boolean hasBirthDayNearby();
}
