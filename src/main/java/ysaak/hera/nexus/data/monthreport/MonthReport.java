package ysaak.hera.nexus.data.monthreport;

import lombok.Data;
import ysaak.hera.nexus.data.Period;
import ysaak.hera.nexus.data.attendance.Attendance;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by ysaak on 22/02/2017.
 */
@Data
public class MonthReport {

  private final LocalDate date;

  private Period<LocalDate> period;

  private List<Attendance> attendances;

  private Map<Integer, WeekSummary> weekSummaries;

  private Duration monthHours;

  private double monthBaseValue;

  private double hourPrice;
  private Duration complementHours;
  private double complementHoursValue;

  private double increasedHourPrice;
  private Duration increasedHours;
  private double increasedHoursValue;
}
