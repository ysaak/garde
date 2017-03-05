package ysaak.hera.nexus.gui.fiche.attendance.add;

import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.buttonbar.EditorButtonBar;
import ysaak.hera.nexus.gui.common.presenter.AbstractFormPresenter;

@Fiche("ATTENDANCE-ADD")
public class AttendanceAddPresenter extends AbstractFormPresenter<Attendance, AttendanceAddView> {

  @Override
  protected AttendanceAddView initView() {
    return new AttendanceAddView();
  }

  @Override
  protected Attendance loadData(Context context) throws Exception {
    return null;
  }

  @Override
  protected void updataData(Attendance data) throws Exception {
    // Not used
  }
}
