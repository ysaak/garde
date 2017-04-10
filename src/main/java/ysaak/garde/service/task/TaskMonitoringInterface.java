package ysaak.garde.service.task;

/**
 * Interface to monitor a task
 */
public interface TaskMonitoringInterface {
  /**
   * Defines the current task type
   * @param type Task type
   */
  void setTaskType(TaskType type);

  /**
   * Indicate that this long task has started not long ago
   */
  void setLongTaskStarted();

  /**
   * Indicate that this long task has ended
   */
  void setLongTaskEnded();
}
