package ysaak.hera.nexus.business.service.monthreport;

import ysaak.hera.nexus.data.monthreport.MonthReport;

import java.time.LocalDate;

/**
 * Created by ysaak on 22/02/2017.
 */
public interface MonthReportService {
  MonthReport getReport(long childId, LocalDate date);
}
