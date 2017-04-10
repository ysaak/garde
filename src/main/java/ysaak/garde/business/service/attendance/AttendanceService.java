package ysaak.garde.business.service.attendance;

import ysaak.garde.data.attendance.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

  /**
   * Create or update an attendance
   * @param childId ID of the child which attended
   * @param attendance Attendance data
   * @return Attendance
   */
  Attendance save(long childId, Attendance attendance);

  /**
   * Find an attendance by its ID
   * @param id ID of an attendance
   * @return Attendance
   */
  Attendance get(long id);

  Attendance get(long childId, LocalDate date);

  List<Attendance> getBetween(long childId, LocalDate startPeriod, LocalDate endPeriod);

  /**
   * Delete an attendance
   * @param attendanceId ID of the attendance to delete
   */
  void delete(long attendanceId);
}
