package ysaak.hera.nexus.gui.fiche.attendance.list;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.actions.ActionType;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;
import ysaak.hera.nexus.service.task.GuiTask;
import ysaak.hera.nexus.service.task.TaskType;

import java.time.LocalDate;
import java.util.List;

@Fiche("ATTENDANCE-LIST")
public class AttendanceListPresenter extends AbstractPresenter<List<Attendance>, AttendanceListView> {

  private class AttendanceDeleteTask extends GuiTask<List<Attendance>> {

    private final List<Attendance> attendances;

    private AttendanceDeleteTask(List<Attendance> attendances) {
      super(TaskType.UPDATE, rootPane);
      this.attendances = attendances;
    }

    @Override
    public List<Attendance> call() throws Exception {
      for (Attendance attendance : attendances) {
        attendanceService.delete(attendance.getId());
      }

      return loadData(currentContext);
    }

    @Override
    public void onSucceeded(List<Attendance> result) {
      setData(result);
    }

    @Override
    public void onFailed(Throwable error) {
      showError(error);
    }
  }

  @Autowired
  private AttendanceService attendanceService;

  @Override
  protected AttendanceListView initView() {
    AttendanceListView view = viewLoader.loadView("fiche/attendance/list");
    view.setMonthSelectorListener(month -> {
      currentContext.setDate(month);
      startLoadData(currentContext);
    });

    return view;
  }

  @Override
  protected List<Attendance> loadData(Context context) throws Exception {

    final LocalDate month;

    if (context.getDate() == null) {
      month = LocalDate.now().withDayOfMonth(1);
      context.setDate(month);
    }
    else {
      month = context.getDate();
    }

    return attendanceService.getBetween(context.getChildId(), month.withDayOfMonth(1), month.plusMonths(1).minusDays(1));
  }

  @Override
  protected void setExtraData() {
    view.setCurrentMonth(currentContext.getDate());
  }

  @Override
  public boolean reloadOnDisplay() {
    return true;
  }

  @Override
  protected void updateData(List<Attendance> data) throws Exception {
    // Not used
  }

  @Override
  protected void onActionEvent(ActionType action, List<Attendance> attendances) {
    if (action == ActionType.CREATE) {
      fireOpenFormRequest("ATTENDANCE-ADD", ContextBuilder.ofChild(currentContext.getChildId()));
    }
    else if (action == ActionType.UPDATE) {
      long attendanceId = attendances.get(0).getId();

      fireOpenFormRequest("ATTENDANCE-ADD", ContextBuilder.get().withChildId(currentContext.getChildId()).withId(attendanceId).build());
    }
    else if (action == ActionType.DELETE) {
      // Delete attendances
      taskFacade.submit(new AttendanceDeleteTask(attendances));
    }
  }
}
