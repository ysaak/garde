package ysaak.hera.nexus.gui.fiche.monthreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ysaak.hera.nexus.business.service.monthreport.MonthReportService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.monthreport.MonthReport;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.buttonbar.EditorButtonBar;
import ysaak.hera.nexus.gui.common.presenter.AbstractFichePresenter;

import java.time.LocalDate;

@Component
@Fiche("MONTHLY-VIEW")
public class MonthlyReportPresenter extends AbstractFichePresenter<MonthReport, MonthlyReportView> {

  @Autowired
  private MonthReportService monthReportService;

  public MonthlyReportPresenter() {
    super(new EditorButtonBar());
  }

  @Override
  protected MonthlyReportView initView() {
    final MonthlyReportView view = new MonthlyReportView(translationFacade);
    view.setMonthChangeEvent(month -> startLoadData(
            ContextBuilder.get().withId(currentContext.getLongId()).withDate(month).build()));
    return view;
  }

  @Override
  protected MonthReport loadData(Context context) throws Exception {
    LocalDate date = (context.getDate() != null) ? context.getDate() : LocalDate.now().withDayOfMonth(1);
    return monthReportService.getReport(context.getLongId(), date);
  }

  @Override
  protected void updataData(MonthReport data) throws Exception {
    // Not used
  }
}
