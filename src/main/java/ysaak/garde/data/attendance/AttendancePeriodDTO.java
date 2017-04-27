package ysaak.garde.data.attendance;

import java.time.LocalTime;

public class AttendancePeriodDTO {

  private Long id;

  private LocalTime startHour;

  private LocalTime endHour;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalTime getStartHour() {
    return startHour;
  }

  public void setStartHour(LocalTime startHour) {
    this.startHour = startHour;
  }

  public LocalTime getEndHour() {
    return endHour;
  }

  public void setEndHour(LocalTime endHour) {
    this.endHour = endHour;
  }
}
