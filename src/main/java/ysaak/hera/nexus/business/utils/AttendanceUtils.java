package ysaak.hera.nexus.business.utils;

import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;

import java.time.Duration;

/**
 * Created by ysaak on 22/02/2017.
 */
public class AttendanceUtils {
  private AttendanceUtils() {}

  public static final Duration calculateDuration(Attendance attendance) {
    Duration duration = null;

    if (attendance != null && attendance.getPeriods() != null) {
      for (AttendancePeriod period : attendance.getPeriods()) {
        final Duration shd = Duration.ofSeconds(period.getStartHour().toSecondOfDay());
        final Duration ehd = Duration.ofSeconds(period.getEndHour().toSecondOfDay());

        if (duration != null) {
          duration = duration.plus(ehd.minus(shd));
        }
        else {
          duration = ehd.minus(shd);
        }
      }
    }

    return (duration != null) ? duration : Duration.ZERO;
  }
}
