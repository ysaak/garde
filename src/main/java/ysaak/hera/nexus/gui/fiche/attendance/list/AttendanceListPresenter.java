package ysaak.hera.nexus.gui.fiche.attendance.list;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.actions.ActionType;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

import java.time.LocalDate;
import java.util.List;

@Fiche("ATTENDANCE-LIST")
public class AttendanceListPresenter extends AbstractPresenter<List<Attendance>, AttendanceListView> {

  @Autowired
  private AttendanceService attendanceService;

  @Override
  protected AttendanceListView initView() {
    return viewLoader.loadView("fiche/attendance/list");
  }

  @Override
  protected List<Attendance> loadData(Context context) throws Exception {

    LocalDate month = LocalDate.now().withDayOfMonth(1);

    System.err.println("Before wait");
    Thread.sleep(5000);
    System.err.println("After wait");

    return attendanceService.getBetween(1, month, month.plusMonths(1).minusDays(1));
  }

  @Override
  protected void updateData(List<Attendance> data) throws Exception {
    // Not used
  }

  @Override
  protected void onActionEvent(ActionType action, List<Attendance> attendances) {



  }
}
