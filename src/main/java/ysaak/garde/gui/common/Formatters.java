package ysaak.garde.gui.common;

import ysaak.garde.data.attendance.AttendancePeriodDTO;
import ysaak.garde.service.translation.I18n;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public final class Formatters {

  private static final DateTimeFormatter TIME_FORMATTER;// = DateTimeFormatter.ofPattern("HH'hmm");
  static {
    TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral("h")
            .optionalStart()
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter();
  }

  private Formatters() { /* private constructor */ }

  public static String formatPeriod(AttendancePeriodDTO period) {

    StringBuilder sb = new StringBuilder();
    sb.append(formatLocalTime(period.getStartHour()))
            .append(" à ")
            .append(formatLocalTime(period.getEndHour()));

    return sb.toString();
  }

  public static String formatLocalTime(LocalTime time) {
    return time.format(TIME_FORMATTER);
  }

  public static String formatDuration(Duration d) {
    return formatDuration(d, ":");
  }

  public static String formatDuration(Duration d, String separator) {
    long hours = d.toHours();
    long minutes = d.minusHours(hours).toMinutes();
    final String format = "%02d" + separator + "%02d";
    return String.format(format, hours, minutes);
  }

  /**
   * Format the age according to the birthday
   * @param birthday Birthday
   * @return Formatted age
   */
  public static String formatAge(LocalDate birthday) {
    if (birthday == null) {
      throw new NullPointerException("birthday is null");
    }

    long yearsDelta = birthday.until(LocalDate.now(), ChronoUnit.YEARS);
    long monthsDelta = birthday.until(LocalDate.now(), ChronoUnit.MONTHS);

    if (yearsDelta > 0) {
      monthsDelta -= yearsDelta * 12;
    }

    if (yearsDelta == 0) {
      return I18n.get("format.age.onlyMonths", monthsDelta);
    }
    else if (monthsDelta == 0) {
      return I18n.get("format.age.onlyYears", yearsDelta);
    }
    else {
      return I18n.get("format.age.full", yearsDelta, monthsDelta);
    }
  }
}
