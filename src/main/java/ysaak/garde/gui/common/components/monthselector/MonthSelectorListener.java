package ysaak.garde.gui.common.components.monthselector;

import java.time.LocalDate;

/**
 * Listener for a month selector
 */
public interface MonthSelectorListener {
  void monthSelected(LocalDate month);
}
