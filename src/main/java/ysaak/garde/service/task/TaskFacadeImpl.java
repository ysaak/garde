package ysaak.garde.service.task;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.service.event.EventFacade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of the task facade
 */
@Service
public class TaskFacadeImpl implements TaskFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskFacadeImpl.class);

  private final ExecutorService execService = Executors.newWorkStealingPool();

  private final ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(10);

  @Autowired
  private EventFacade eventFacade;

  @Override
  public <T> void submit(GuiTask<T> task) {
    execService.submit(new GuiTaskRunner<>(task));
  }

  @Override
  public void shutdown() {
    shutdownExecutor(execService, "main");
    shutdownExecutor(scheduledService, "scheduler");
  }

  /**
   * Shutdown a specific executor
   * @param executor Executor to shutdown
   * @param name Executor name
   */
  private void shutdownExecutor(ExecutorService executor, String name) {
    try {
      LOGGER.debug("Attempt to shutdown \""+name+"\" executor");
      executor.shutdown();
      executor.awaitTermination(5, TimeUnit.SECONDS);
    }
    catch (InterruptedException e) {
      LOGGER.debug("Tasks interrupted");
    }
    finally {
      if (!executor.isTerminated()) {
        LOGGER.debug("Cancelling unfinished tasks");
      }
      executor.shutdownNow();
      LOGGER.debug("Shutdown of \"" + name + "\" executor complete");
    }
  }

  private class GuiTaskRunner<T> implements Runnable {

    private final GuiTask<T> task;

    private Future<Void> longTaskDetector;

    private GuiTaskRunner(GuiTask<T> task) {
      this.task = task;
    }

    @Override
    public void run() {
      //taskSubmitted();

      longTaskDetector = scheduledService.schedule(() -> {
        task.setLongTaskStarted();
        longTaskDetector = null;
        return null;
      }, 200, TimeUnit.MILLISECONDS);

      try {
        final T data = task.call();
        Platform.runLater(() -> task.onSucceeded(data));
        taskEnded2();
      }
      catch (Throwable error) {
        Platform.runLater(() -> task.onFailed(error));
        taskEnded2();
      }
    }

    private void taskEnded2() {
      if (longTaskDetector != null) {
        longTaskDetector.cancel(true);
        longTaskDetector = null;
      }

      task.setLongTaskEnded();
    }
  }
}