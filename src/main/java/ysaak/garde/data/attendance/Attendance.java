package ysaak.garde.data.attendance;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Attendance implements Comparable<Attendance> {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Long contractId;
  
  @Column
  private LocalDate date;
  
  @Column
  @Enumerated
  private MaintenanceFee maintenanceFee;
  
  @Column
  @Enumerated
  private MealFee mealFee;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "attendance")
  private List<AttendancePeriod> periods;

  @Override
  public int compareTo(Attendance o) {
    return date.compareTo(o.getDate());
  }
}
