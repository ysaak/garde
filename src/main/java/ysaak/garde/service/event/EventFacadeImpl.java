package ysaak.garde.service.event;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

@Service
public class EventFacadeImpl implements EventFacade {

  private final EventBus eventbus = new EventBus("mainbus");
  
  @Override
  public void register(Object object) {
    eventbus.register(object);
  }

  @Override
  public void unregister(Object object) {
    eventbus.unregister(object);
  }
  
  @Override
  public void post(Object event) {
    eventbus.post(event);
  }
}
