package ysaak.garde.gui.fiche.attendance.list;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.gui.common.ContextBuilder;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.service.task.GuiTask;
import ysaak.garde.business.service.attendance.AttendanceService;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.actions.ActionType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;
import ysaak.garde.service.task.TaskType;

import java.time.LocalDate;
import java.util.List;

@Fiche("ATTENDANCE-LIST")
public class AttendanceListPresenter extends AbstractPresenter<List<AttendanceDTO>, AttendanceListView> {

  private class AttendanceDeleteTask extends GuiTask<List<AttendanceDTO>> {

    private final List<AttendanceDTO> attendances;

    private AttendanceDeleteTask(List<AttendanceDTO> attendances) {
      super(TaskType.UPDATE, rootPane);
      this.attendances = attendances;
    }

    @Override
    public List<AttendanceDTO> call() throws Exception {
      for (AttendanceDTO attendance : attendances) {
        attendanceService.delete(attendance.getId());
      }

      return loadData(currentContext);
    }

    @Override
    public void onSucceeded(List<AttendanceDTO> result) {
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
  protected List<AttendanceDTO> loadData(Context context) throws Exception {

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
  protected void updateData(List<AttendanceDTO> data) throws Exception {
    // Not used
  }

  @Override
  protected void onActionEvent(ActionType action, List<AttendanceDTO> attendances) {
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
