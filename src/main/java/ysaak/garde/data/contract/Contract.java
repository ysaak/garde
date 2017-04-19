package ysaak.garde.data.contract;

import java.time.LocalDate;

import lombok.Data;
import ysaak.garde.business.model.Child;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Contract {
  /**
   * Contract ID
   */
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  /**
   * Child
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  private Child child;

  /**
   * Type of the contract
   */
  @Column(nullable = false)
  @Enumerated
  private ContractType type;

  /**
   * Start date of the contract (inclusive)
   */
  @Column(nullable = false)
  private LocalDate startDate;

  /**
   * End date of the contract (inclusive)
   */
  @Column(nullable = false)
  private LocalDate endDate;

  /**
   * Attendance weeks per year
   */
  @Column(nullable = false)
  private int weekPerYear;

  /**
   * Attendance per weeks
   */
  @Column(nullable = false)
  private double attendancePerWeek;

  /**
   * Hours per week
   */
  @Column(nullable = false)
  private double hoursPerWeek;

  /**
   * Base value of an hour
   */
  @Column(nullable = false)
  private double baseHourPrice;

  /**
   * Indicate if an increased hour price is defined
   */
  @Column(nullable = false)
  private boolean hasIncreasedHourPrice;

  /**
   * Increased value of an hour
   */
  @Column
  private Double increasedHourValue;
}
