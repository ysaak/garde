package ysaak.garde.gui.fiche.monthreport;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.monthreport.MonthReportService;
import ysaak.garde.data.monthreport.MonthReport;
import ysaak.garde.gui.common.ContextBuilder;
import ysaak.garde.gui.common.annotation.Module;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

import java.time.LocalDate;

@Module("MONTHLY-VIEW")
public class MonthlyReportPresenter extends AbstractPresenter<MonthReport, MonthlyReportView> {

  @Autowired
  private MonthReportService monthReportService;

  @Override
  protected MonthlyReportView initView() {
    final MonthlyReportView view = viewLoader.loadView(MonthlyReportView.class);
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
  protected void updateData(MonthReport data) throws Exception {
    // Not used
  }
}
