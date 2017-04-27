package ysaak.garde.gui.fiche.attendance.add;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.business.service.attendance.AttendanceService;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

@Fiche("ATTENDANCE-ADD")
public class AttendanceAddPresenter extends AbstractPresenter<AttendanceDTO, AttendanceAddView> {

  @Autowired
  private AttendanceService attendanceService;

  public AttendanceAddPresenter() {
    super(ButtonBarType.EDIT);
  }

  @Override
  protected AttendanceDTO loadData(Context context) throws Exception {
    return attendanceService.get(context.getLongId());
  }

  @Override
  protected void updateData(AttendanceDTO data) throws Exception {
    attendanceService.save(data);


    System.err.println(data);
  }
}
