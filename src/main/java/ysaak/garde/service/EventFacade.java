package ysaak.garde.service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;

public final class EventFacade {
  private EventFacade() { /* masked */ }

  private static final EventBus EVENT_BUS = new EventBus("app-main-bus");

  /**
   * Registers all subscriber methods on {@code object} to receive events.
   *
   * @param object object whose subscriber methods should be registered.
   */
  public static void register(Object object) {
    EVENT_BUS.register(object);
  }
  
  /**
   * Unregisters all subscriber methods on a registered {@code object}.
   *
   * @param object object whose subscriber methods should be unregistered.
   * @throws IllegalArgumentException if the object was not previously registered.
   */
  public static void unregister(Object object) {
    EVENT_BUS.unregister(object);
  }
  
  /**
   * Posts an event to all registered subscribers. This method will return successfully after the
   * event has been posted to all subscribers, and regardless of any exceptions thrown by
   * subscribers.
   *
   * <p>If no subscribers have been subscribed for {@code event}'s class, and {@code event} is not
   * already a {@link DeadEvent}, it will be wrapped in a DeadEvent and reposted.
   *
   * @param event event to post.
   */
  public static void post(Object event) {
    EVENT_BUS.post(event);
  }
}
