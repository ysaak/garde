package ysaak.hera.nexus.gui.fiche.attendance.list;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.buttonbar.FinishButtonBar;
import ysaak.hera.nexus.gui.common.presenter.AbstractFormPresenter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Fiche("ATTENDANCE-LIST")
public class AttendanceListPresenter extends AbstractFormPresenter<List<Attendance>, AttendanceListView> {

  @Autowired
  private AttendanceService attendanceService;
  
  public AttendanceListPresenter() throws IOException {
    super(new FinishButtonBar());
  }

  @Override
  protected AttendanceListView initView() {
    return viewLoader.loadView("fiche/attendance/list");
  }

  @Override
  protected List<Attendance> loadData(Context context) throws Exception {

    LocalDate month = LocalDate.now().withDayOfMonth(1);

    return attendanceService.getBetween(1, month, month.plusMonths(1).minusDays(1));
  }

  @Override
  protected void updataData(List<Attendance> data) throws Exception {
    // Not used
  }
}
