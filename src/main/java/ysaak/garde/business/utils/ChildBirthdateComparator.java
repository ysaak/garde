package ysaak.garde.business.utils;

import java.time.MonthDay;
import java.util.Comparator;

import ysaak.garde.data.Child;

public class ChildBirthdateComparator implements Comparator<Child> {

  @Override
  public int compare(Child o1, Child o2) {
    
    MonthDay cmd1 = MonthDay.of(o1.getBirthDate().getMonth(), o1.getBirthDate().getDayOfMonth());
    MonthDay cmd2 = MonthDay.of(o2.getBirthDate().getMonth(), o2.getBirthDate().getDayOfMonth());
    
    return cmd1.compareTo(cmd2);
  }

}
