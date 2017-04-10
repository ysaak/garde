package ysaak.garde.data.attendance;

import java.time.LocalTime;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"attendance"})
public class AttendancePeriod {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  private Attendance attendance;
  
  @Column
  private LocalTime startHour;
  
  @Column
  private LocalTime endHour;
}
