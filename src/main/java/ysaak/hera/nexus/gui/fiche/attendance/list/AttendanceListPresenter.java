package ysaak.hera.nexus.gui.fiche.attendance.list;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.buttonbar.FinishButtonBar;
import ysaak.hera.nexus.gui.common.presenter.AbstractFXMLPresenter;

@Component
@Fiche("ATTENDANCE-LIST")
public class AttendanceListPresenter extends AbstractFXMLPresenter<List<Attendance>, AttendanceListView> {

  @Autowired
  private AttendanceService attendanceService;
  
  public AttendanceListPresenter() throws IOException {
    super("fiche/attendance/list", new FinishButtonBar());
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
