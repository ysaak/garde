package ysaak.hera.nexus.business.service.child;

import java.util.List;

import ysaak.hera.nexus.data.Child;

public interface ChildService {
  
  Child create(Child child);
  
  List<Child> listAll();
  
  boolean hasBirthDayNearby();
}
