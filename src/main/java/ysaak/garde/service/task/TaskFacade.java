package ysaak.garde.service.task;

/**
 * Facade for task management
 */
public interface TaskFacade {

  /**
   * Execute task
   * @param task Task to execute
   * @param <T> Expected type
   */
  <T> void submit(GuiTask<T> task);

  /**
   * Shutdown tasks service
   */
  void shutdown();

  /**
   * Sets the task monitoring interface
   * @param monitoringInterface Task monitoring interface
   */
  void setTaskMonitoringInterface(TaskMonitoringInterface monitoringInterface);
}
