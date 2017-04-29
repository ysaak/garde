package ysaak.garde.data.monthreport;

import ysaak.garde.data.Period;
import ysaak.garde.data.attendance.AttendanceDTO;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by ysaak on 22/02/2017.
 */
public class MonthReport {

  private final LocalDate date;

  private Period<LocalDate> period;

  private List<AttendanceDTO> attendances;

  private Map<Integer, WeekSummary> weekSummaries;

  private Duration monthHours;

  private double monthBaseValue;

  private double hourPrice;
  private Duration complementHours;
  private double complementHoursValue;

  private double increasedHourPrice;
  private Duration increasedHours;
  private double increasedHoursValue;

  public MonthReport(LocalDate date) {
    this.date = date;
  }

  public LocalDate getDate() {
    return date;
  }

  public Period<LocalDate> getPeriod() {
    return period;
  }

  public void setPeriod(Period<LocalDate> period) {
    this.period = period;
  }

  public List<AttendanceDTO> getAttendances() {
    return attendances;
  }

  public void setAttendances(List<AttendanceDTO> attendances) {
    this.attendances = attendances;
  }

  public Map<Integer, WeekSummary> getWeekSummaries() {
    return weekSummaries;
  }

  public void setWeekSummaries(Map<Integer, WeekSummary> weekSummaries) {
    this.weekSummaries = weekSummaries;
  }

  public Duration getMonthHours() {
    return monthHours;
  }

  public void setMonthHours(Duration monthHours) {
    this.monthHours = monthHours;
  }

  public double getMonthBaseValue() {
    return monthBaseValue;
  }

  public void setMonthBaseValue(double monthBaseValue) {
    this.monthBaseValue = monthBaseValue;
  }

  public double getHourPrice() {
    return hourPrice;
  }

  public void setHourPrice(double hourPrice) {
    this.hourPrice = hourPrice;
  }

  public Duration getComplementHours() {
    return complementHours;
  }

  public void setComplementHours(Duration complementHours) {
    this.complementHours = complementHours;
  }

  public double getComplementHoursValue() {
    return complementHoursValue;
  }

  public void setComplementHoursValue(double complementHoursValue) {
    this.complementHoursValue = complementHoursValue;
  }

  public double getIncreasedHourPrice() {
    return increasedHourPrice;
  }

  public void setIncreasedHourPrice(double increasedHourPrice) {
    this.increasedHourPrice = increasedHourPrice;
  }

  public Duration getIncreasedHours() {
    return increasedHours;
  }

  public void setIncreasedHours(Duration increasedHours) {
    this.increasedHours = increasedHours;
  }

  public double getIncreasedHoursValue() {
    return increasedHoursValue;
  }

  public void setIncreasedHoursValue(double increasedHoursValue) {
    this.increasedHoursValue = increasedHoursValue;
  }
}
