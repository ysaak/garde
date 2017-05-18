package ysaak.garde.gui.events;

/**
 * Event indicating a child selection
 */
public class ChildSelectionEvent {
  private final Long childId;

  public ChildSelectionEvent(Long childId) {
    this.childId = childId;
  }

  public Long getChildId() {
    return childId;
  }
}
