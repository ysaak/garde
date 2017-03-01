package ysaak.hera.nexus.gui.common;

import ysaak.hera.nexus.data.attendance.AttendancePeriod;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;

public class Formatters {

  private static final DateTimeFormatter TIME_FORMATTER;// = DateTimeFormatter.ofPattern("HH'hmm");
  static {
    TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral("h")
            .optionalStart()
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter();
  }

  public static  final String formatPeriod(AttendancePeriod period) {

    StringBuilder sb = new StringBuilder();
    sb.append(formatLocalTime(period.getStartHour()))
            .append(" à ")
            .append(formatLocalTime(period.getEndHour()));

    return sb.toString();
  }

  public static final String formatLocalTime(LocalTime time) {
    String strTime = time.format(TIME_FORMATTER);
    return strTime;
  }

  public static final String formatDuration(Duration d) {
    return formatDuration(d, ":");
  }

  public static final String formatDuration(Duration d, String separator) {
    long hours = d.toHours();
    long minutes = d.minusHours(hours).toMinutes();
    
    return String.format("%02d" + separator + "%02d", hours, minutes);
  }
}
