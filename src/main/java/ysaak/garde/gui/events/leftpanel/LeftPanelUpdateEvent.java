package ysaak.garde.gui.events.leftpanel;

public class LeftPanelUpdateEvent {
  private final Long childId;

  public LeftPanelUpdateEvent(Long childId) {
    this.childId = childId;
  }

  public Long getChildId() {
    return childId;
  }
}
