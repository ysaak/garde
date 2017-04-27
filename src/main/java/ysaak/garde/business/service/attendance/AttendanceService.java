package ysaak.garde.business.service.attendance;

import ysaak.garde.data.attendance.AttendanceDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

  /**
   * Create or update an attendance
   * @param attendance Attendance data
   * @return Attendance
   */
  AttendanceDTO save(AttendanceDTO attendance);

  /**
   * Find an attendance by its ID
   * @param id ID of an attendance
   * @return Attendance
   */
  AttendanceDTO get(long id);

  AttendanceDTO get(long contractId, LocalDate date);

  List<AttendanceDTO> getBetween(long contractId, LocalDate startPeriod, LocalDate endPeriod);

  /**
   * Delete an attendance
   * @param attendanceId ID of the attendance to delete
   */
  void delete(long attendanceId);
}
