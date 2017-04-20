package ysaak.garde.data.contract;

import ysaak.garde.business.model.Child;

import java.time.LocalDate;

/**
 * Contract DTO
 */
public class ContractDTO {
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

  public ContractType getType() {
    return type;
  }

  public void setType(ContractType type) {
    this.type = type;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getWeekPerYear() {
    return weekPerYear;
  }

  public void setWeekPerYear(int weekPerYear) {
    this.weekPerYear = weekPerYear;
  }

  public double getAttendancePerWeek() {
    return attendancePerWeek;
  }

  public void setAttendancePerWeek(double attendancePerWeek) {
    this.attendancePerWeek = attendancePerWeek;
  }

  public double getHoursPerWeek() {
    return hoursPerWeek;
  }

  public void setHoursPerWeek(double hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  public double getBaseHourPrice() {
    return baseHourPrice;
  }

  public void setBaseHourPrice(double baseHourPrice) {
    this.baseHourPrice = baseHourPrice;
  }

  public boolean isHasIncreasedHourPrice() {
    return hasIncreasedHourPrice;
  }

  public void setHasIncreasedHourPrice(boolean hasIncreasedHourPrice) {
    this.hasIncreasedHourPrice = hasIncreasedHourPrice;
  }

  public Double getIncreasedHourValue() {
    return increasedHourValue;
  }

  public void setIncreasedHourValue(Double increasedHourValue) {
    this.increasedHourValue = increasedHourValue;
  }
}
