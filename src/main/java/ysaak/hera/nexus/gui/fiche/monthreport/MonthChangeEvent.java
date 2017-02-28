package ysaak.hera.nexus.gui.fiche.monthreport;

import java.time.LocalDate;

/**
 * Created by ysaak on 22/02/2017.
 */
public interface MonthChangeEvent {
  void monthChanged(LocalDate date);
}
