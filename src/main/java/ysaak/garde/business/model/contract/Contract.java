package ysaak.garde.business.model.contract;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Range;
import ysaak.garde.business.model.Child;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Contract {
  /**
   * Contract ID
   */
  private Long id;

  /**
   * Child
   */
  @NotNull
  private Child child;

  /**
   * Type of the contract
   */
  @NotNull
  private ContractType type;

  /**
   * Status of the contract
   */
  private transient ContractStatus status;

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
  @NotNull
  @Range(min = 1, max = 52)
  private Integer weekPerYear;

  /**
   * Attendance per weeks
   */
  @NotNull
  @Range(min = 1, max = 7)
  private Integer attendancePerWeek;

  /**
   * Hours per week
   */
  @NotNull
  @Range
  private Double hoursPerWeek;

  /**
   * Base value of an hour
   */
  @NotNull
  @Range(min = 1)
  private Double baseHourPrice;

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
  @JoinColumn(name = "child_id", nullable = false)
  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

  @Column(nullable = false, length = 3)
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

  @Column
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Column
  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Column(nullable = false, length = 2)
  public Integer getWeekPerYear() {
    return weekPerYear;
  }

  public void setWeekPerYear(Integer weekPerYear) {
    this.weekPerYear = weekPerYear;
  }

  @Column(nullable = false)
  public Integer getAttendancePerWeek() {
    return attendancePerWeek;
  }

  public void setAttendancePerWeek(Integer attendancePerWeek) {
    this.attendancePerWeek = attendancePerWeek;
  }

  @Column(nullable = false)
  public Double getHoursPerWeek() {
    return hoursPerWeek;
  }

  public void setHoursPerWeek(Double hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  @Column(nullable = false)
  public Double getBaseHourPrice() {
    return baseHourPrice;
  }

  public void setBaseHourPrice(Double baseHourPrice) {
    this.baseHourPrice = baseHourPrice;
  }

  @Column
  public Double getIncreasedHourValue() {
    return increasedHourValue;
  }

  public void setIncreasedHourValue(Double increasedHourValue) {
    this.increasedHourValue = increasedHourValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Contract)) return false;

    Contract other = (Contract) obj;

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
