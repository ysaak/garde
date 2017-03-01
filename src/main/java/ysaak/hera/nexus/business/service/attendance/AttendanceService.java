package ysaak.hera.nexus.business.service.attendance;

import java.time.LocalDate;
import java.util.List;

import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;

public interface AttendanceService {

  Attendance create(long childId, Attendance attendance);

  Attendance get(long childId, LocalDate date);

  List<Attendance> getBetween(long childId, LocalDate startPeriod, LocalDate endPeriod);
}
