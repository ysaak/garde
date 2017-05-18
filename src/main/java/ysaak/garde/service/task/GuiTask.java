package ysaak.garde.service.task;

/**
 * Listener for task execution
 */
public abstract class GuiTask<T> {
  private final TaskType type;

  public GuiTask() {
    this(TaskType.LOAD);
  }

  public GuiTask(TaskType type) {
    this.type = type;
  }

  public abstract T call() throws Exception;

  public abstract void onSucceeded(T result);

  public abstract void onFailed(Throwable error);

  public TaskType getType() {
    return type;
  }

}
