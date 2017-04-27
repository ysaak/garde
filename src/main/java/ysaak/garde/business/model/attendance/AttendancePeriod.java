package ysaak.garde.business.model.attendance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
public class AttendancePeriod {
  private Long id;

  private Attendance attendance;
  
  private LocalTime startHour;
  
  private LocalTime endHour;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  public Attendance getAttendance() {
    return attendance;
  }

  public void setAttendance(Attendance attendance) {
    this.attendance = attendance;
  }

  @Column(nullable = false)
  public LocalTime getStartHour() {
    return startHour;
  }

  public void setStartHour(LocalTime startHour) {
    this.startHour = startHour;
  }

  @Column(nullable = false)
  public LocalTime getEndHour() {
    return endHour;
  }

  public void setEndHour(LocalTime endHour) {
    this.endHour = endHour;
  }
}
