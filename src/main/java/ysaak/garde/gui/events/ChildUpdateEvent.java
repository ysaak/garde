package ysaak.garde.gui.events;

public class ChildUpdateEvent {
  private final Long childId;

  public ChildUpdateEvent(Long childId) {
    this.childId = childId;
  }

  public Long getChildId() {
    return childId;
  }
}
