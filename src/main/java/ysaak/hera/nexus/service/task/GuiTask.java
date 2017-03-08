package ysaak.hera.nexus.service.task;

import javafx.application.Platform;

/**
 * Listener for task execution
 */
public abstract class GuiTask<T> {
  private final TaskType type;

  private final TaskMonitoringInterface monitoringInterface;

  public GuiTask() {
    this(null, null);
  }

  public GuiTask(TaskType type, TaskMonitoringInterface monitoringInterface) {
    this.type = type;
    this.monitoringInterface = monitoringInterface;
  }

  public abstract T call() throws Exception;

  public abstract void onSucceeded(T result);

  public abstract void onFailed(Throwable error);

  public void setLongTaskStarted() {
    if (monitoringInterface != null) {
      Platform.runLater(() -> {
        monitoringInterface.setTaskType(type);
        monitoringInterface.setLongTaskStarted();
      });
    }
  }

  public void setLongTaskEnded() {
    if (monitoringInterface != null) {
      Platform.runLater(monitoringInterface::setLongTaskEnded);
    }
  }
}
