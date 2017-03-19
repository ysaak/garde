package ysaak.hera.nexus.gui.fiche.attendance.add;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

@Fiche("ATTENDANCE-ADD")
public class AttendanceAddPresenter extends AbstractPresenter<Attendance, AttendanceAddView> {

  @Autowired
  private AttendanceService attendanceService;

  @Override
  protected Attendance loadData(Context context) throws Exception {
    return attendanceService.get(context.getLongId());
  }

  @Override
  protected void updateData(Attendance data) throws Exception {
    attendanceService.save(currentContext.getChildId(), data);


    System.err.println(data);
  }
}
