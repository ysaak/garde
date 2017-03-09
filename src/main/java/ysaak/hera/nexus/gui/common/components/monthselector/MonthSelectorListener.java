package ysaak.hera.nexus.gui.common.components.monthselector;

import java.time.LocalDate;

/**
 * Listener for a month selector
 */
public interface MonthSelectorListener {
  void monthSelected(LocalDate month);
}
