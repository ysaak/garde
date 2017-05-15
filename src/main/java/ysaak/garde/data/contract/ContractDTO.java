package ysaak.garde.data.contract;

import com.google.common.base.MoreObjects;
import ysaak.garde.data.ChildDTO;

import java.time.LocalDate;
import java.util.Objects;

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
  private ChildDTO child;

  /**
   * Type of the contract
   */
  private ContractType type;

  /**
   * Status of the contract
   */
  private ContractStatus status;

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
  private Integer weekPerYear;

  /**
   * Attendance per weeks
   */
  private Integer attendancePerWeek;

  /**
   * Hours per week
   */
  private Integer hoursPerWeek;

  /**
   * Base value of an hour
   */
  private Double baseHourPrice;

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

  public ChildDTO getChild() {
    return child;
  }

  public void setChild(ChildDTO child) {
    this.child = child;
  }

  public ContractType getType() {
    return type;
  }

  public void setType(ContractType type) {
    this.type = type;
  }

  public ContractStatus getStatus() {
    return status;
  }

  public void setStatus(ContractStatus status) {
    this.status = status;
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

  public Integer getWeekPerYear() {
    return weekPerYear;
  }

  public void setWeekPerYear(Integer weekPerYear) {
    this.weekPerYear = weekPerYear;
  }

  public Integer getAttendancePerWeek() {
    return attendancePerWeek;
  }

  public void setAttendancePerWeek(Integer attendancePerWeek) {
    this.attendancePerWeek = attendancePerWeek;
  }

  public Integer getHoursPerWeek() {
    return hoursPerWeek;
  }

  public void setHoursPerWeek(Integer hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  public Double getBaseHourPrice() {
    return baseHourPrice;
  }

  public void setBaseHourPrice(Double baseHourPrice) {
    this.baseHourPrice = baseHourPrice;
  }

  public Double getIncreasedHourValue() {
    return increasedHourValue;
  }

  public void setIncreasedHourValue(Double increasedHourValue) {
    this.increasedHourValue = increasedHourValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof ContractDTO)) return false;

    ContractDTO other = (ContractDTO) obj;

    return Objects.equals(id, other.id)
            && Objects.equals(child, other.child)
            && Objects.equals(type, other.type)
            && Objects.equals(status, other.status)
            && Objects.equals(startDate, other.startDate)
            && Objects.equals(endDate, other.endDate)
            && Objects.equals(weekPerYear, other.weekPerYear)
            && Objects.equals(attendancePerWeek, other.attendancePerWeek)
            && Objects.equals(hoursPerWeek, other.hoursPerWeek)
            && Objects.equals(baseHourPrice, other.baseHourPrice)
            && Objects.equals(increasedHourValue, other.increasedHourValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, status, startDate, endDate, weekPerYear, attendancePerWeek, hoursPerWeek, baseHourPrice, increasedHourValue);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("child", child)
            .add("type", type)
            .add("status", status)
            .add("startDate", startDate)
            .add("endDate", endDate)
            .add("weekPerYear", weekPerYear)
            .add("attendancePerWeek", attendancePerWeek)
            .add("hoursPerWeek", hoursPerWeek)
            .add("baseHourPrice", baseHourPrice)
            .add("increasedHourValue", increasedHourValue)
            .toString();
  }
}
