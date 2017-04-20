package ysaak.garde.business.model.contract;

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
import java.time.LocalDate;

@Entity
public class Contract {
  /**
   * Contract ID
   */
  private Long id;

  /**
   * Child
   */
  private Child child;

  /**
   * Type of the contract
   */
  private ContractType type;

  /**
   * Start date of the contract (inclusive)
   */
  private LocalDate startDate;

  /**
   * End date of the contract (inclusive)
   */
  private LocalDate endDate;

  /**
   * Attendance weeks per year
   */
  private int weekPerYear;

  /**
   * Attendance per weeks
   */
  private double attendancePerWeek;

  /**
   * Hours per week
   */
  private double hoursPerWeek;

  /**
   * Base value of an hour
   */
  private double baseHourPrice;

  /**
   * Indicate if an increased hour price is defined
   */
  private boolean hasIncreasedHourPrice;

  /**
   * Increased value of an hour
   */
  private Double increasedHourValue;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

  @Column(nullable = false)
  @Enumerated
  public ContractType getType() {
    return type;
  }

  public void setType(ContractType type) {
    this.type = type;
  }

  @Column(nullable = false)
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Column(nullable = false)
  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Column(nullable = false)
  public int getWeekPerYear() {
    return weekPerYear;
  }

  public void setWeekPerYear(int weekPerYear) {
    this.weekPerYear = weekPerYear;
  }

  @Column(nullable = false)
  public double getAttendancePerWeek() {
    return attendancePerWeek;
  }

  public void setAttendancePerWeek(double attendancePerWeek) {
    this.attendancePerWeek = attendancePerWeek;
  }

  @Column(nullable = false)
  public double getHoursPerWeek() {
    return hoursPerWeek;
  }

  public void setHoursPerWeek(double hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  @Column(nullable = false)
  public double getBaseHourPrice() {
    return baseHourPrice;
  }

  public void setBaseHourPrice(double baseHourPrice) {
    this.baseHourPrice = baseHourPrice;
  }

  @Column(nullable = false)
  public boolean isHasIncreasedHourPrice() {
    return hasIncreasedHourPrice;
  }

  public void setHasIncreasedHourPrice(boolean hasIncreasedHourPrice) {
    this.hasIncreasedHourPrice = hasIncreasedHourPrice;
  }

  @Column
  public Double getIncreasedHourValue() {
    return increasedHourValue;
  }

  public void setIncreasedHourValue(Double increasedHourValue) {
    this.increasedHourValue = increasedHourValue;
  }
}
