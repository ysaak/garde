package ysaak.garde.business.service.monthreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.data.monthreport.MonthReport;
import ysaak.garde.business.service.attendance.AttendanceService;
import ysaak.garde.business.utils.AttendanceUtils;
import ysaak.garde.data.Period;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.data.monthreport.WeekSummary;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class MonthReportServiceImpl implements MonthReportService {

  @Autowired
  private AttendanceService attendanceService;

  @Override
  public MonthReport getReport(long childId, LocalDate date) {
    final MonthReport report = new MonthReport(date.withDayOfMonth(1));

    Period<LocalDate> period = calculatePeriod(date);
    report.setPeriod(period);

    // Infos du contrat
    Duration expectedWeekDuration = Duration.ofHours(20);
    boolean workOnSaturday = false;
    double hourPrice = 3.;
    double increasedHourPrice = 5.;
    double monthBaseValue = 100.;


    // Fetch attendances
    List<AttendanceDTO> attendances = attendanceService.getBetween(childId, period.getStart(), period.getEnd());
    attendances.sort(Comparator.comparing(AttendanceDTO::getDate));
    report.setAttendances(attendances);

    // Calculate week summaries
    final Map<Integer, WeekSummary> weekSummaries = calculateWeekSummaries(attendances, period, date.getMonth(), expectedWeekDuration, workOnSaturday);
    report.setWeekSummaries(weekSummaries);

    // Calculate month extra duration
    Duration monthHours = Duration.ZERO;
    Duration complementHours = Duration.ZERO;
    Duration increasedHours = Duration.ZERO;

    for (WeekSummary summary : weekSummaries.values()) {

      monthHours = monthHours.plus(summary.getMonthHours());

      if (summary.isCountedInCurrentMonth()) {
        complementHours = complementHours.plus(summary.getComplementHours());
        increasedHours = increasedHours.plus(summary.getIncreasedHours());
      }
    }

    report.setMonthHours(monthHours);
    report.setMonthBaseValue(monthBaseValue);

    report.setHourPrice(hourPrice);
    report.setComplementHours(complementHours);
    report.setComplementHoursValue(calculateHoursValue(complementHours, hourPrice));

    report.setIncreasedHours(increasedHours);
    report.setIncreasedHourPrice(increasedHourPrice);
    report.setIncreasedHourPrice(calculateHoursValue(increasedHours, increasedHourPrice));

    return report;
  }

  private double calculateHoursValue(Duration duration, double value) {
    double hours = duration.toMinutes() / 60.;
    return hours * value;
  }

  /**
   * Calculate report period
   * @param date Month
   * @return Report period
   */
  private Period<LocalDate> calculatePeriod(LocalDate date) {
    final TemporalField dayOfWeekField = WeekFields.of(Locale.getDefault()).dayOfWeek();

    final LocalDate firstDayOfMonth = date.withDayOfMonth(1);

    // Calcul du 1er jour de visible
    final LocalDate firstDayOfReport;
    if (firstDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY) {
      firstDayOfReport = firstDayOfMonth.plusDays(1);
    }
    else if (firstDayOfMonth.getDayOfWeek() != DayOfWeek.MONDAY) {
      firstDayOfReport = firstDayOfMonth.with(dayOfWeekField, 1);
    }
    else {
      firstDayOfReport = firstDayOfMonth;
    }

    // Calcul du dernier jour visible
    final LocalDate lastDayOfReport;


    final LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
    if (Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(lastDayOfMonth.getDayOfWeek())) {
      lastDayOfReport = lastDayOfMonth;
    }
    else {
      lastDayOfReport = lastDayOfMonth.with(dayOfWeekField, DayOfWeek.SATURDAY.getValue());
    }

    return new Period<>(firstDayOfReport, lastDayOfReport);
  }

  /**
   * Calculate week summaries
   * @param attendances List of attendance
   * @param period Report period
   * @param currentMonth Current month
   * @param expectedWeekDuration Expected week duration
   * @return Week summaries
   */
  private Map<Integer, WeekSummary> calculateWeekSummaries(List<AttendanceDTO> attendances, Period<LocalDate> period, Month currentMonth, Duration expectedWeekDuration, boolean workOnSaturday) {
    final TemporalField woyField = WeekFields.of(Locale.getDefault()).weekOfYear();
    final Map<Integer, WeekSummary> weekSummaries = new HashMap<>();

    int currentWeek = period.getStart().get(woyField);

    Duration weekDuration = Duration.ZERO;
    Duration weekMonthDuration = Duration.ZERO;
    Duration monthDuration = Duration.ZERO;

    // Calcul des résumé des semaines
    for (AttendanceDTO attendance : attendances) {

      int attWeek = attendance.getDate().get(woyField);
      if (attWeek != currentWeek) {
        weekSummaries.put(currentWeek, getWeekSummary(weekDuration, weekMonthDuration, expectedWeekDuration));

        currentWeek = attWeek;
        weekDuration = Duration.ZERO;
        weekMonthDuration = Duration.ZERO;
      }

      Duration attendanceDuration = AttendanceUtils.calculateDuration(attendance);

      weekDuration = weekDuration.plus(attendanceDuration);

      if (attendance.getDate().getMonth() == currentMonth) {
        monthDuration = monthDuration.plus(attendanceDuration);
        weekMonthDuration = weekMonthDuration.plus(attendanceDuration);
      }
    }

    weekSummaries.put(currentWeek, getWeekSummary(weekDuration, weekMonthDuration, expectedWeekDuration));

    // Check if week extra hours will be count for the current month
    LocalDate lastDay = period.getEnd();

    if (lastDay.getDayOfWeek() == DayOfWeek.SUNDAY || (!workOnSaturday && lastDay.getDayOfWeek() == DayOfWeek.SATURDAY)) {
      weekSummaries.get(currentWeek).setCountedInCurrentMonth(false);
    }

    return weekSummaries;
  }

  /**
   * Aggregate data to create a week summary
   * @param totalDuration Total hours of the week
   * @param monthDuration Total hours of the week in the current month
   * @param expectedWeekHours Expected hours of the week
   * @return Week summary
   */
  private WeekSummary getWeekSummary(Duration totalDuration, Duration monthDuration, Duration expectedWeekHours) {

    final Duration increasedHours;
    final Duration complementHours;

    Duration tempDuration = totalDuration;

    if (totalDuration.toHours() > 45) {
      increasedHours = totalDuration.minusHours(45);
      tempDuration = tempDuration.minus(increasedHours);
    }
    else {
      increasedHours = Duration.ZERO;
    }

    if (tempDuration.compareTo(expectedWeekHours) > 0) {
      complementHours = tempDuration.minus(expectedWeekHours);
    }
    else {
      complementHours = Duration.ZERO;
    }

    return new WeekSummary(totalDuration, monthDuration, complementHours, increasedHours);
  }
}
