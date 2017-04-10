package ysaak.garde.business.service.monthreport;

import ysaak.garde.data.monthreport.MonthReport;

import java.time.LocalDate;

/**
 * Created by ysaak on 22/02/2017.
 */
public interface MonthReportService {
  MonthReport getReport(long childId, LocalDate date);
}
