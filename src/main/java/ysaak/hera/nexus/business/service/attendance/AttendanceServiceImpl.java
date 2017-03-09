package ysaak.hera.nexus.business.service.attendance;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.hera.nexus.business.repository.AttendanceRepository;
import ysaak.hera.nexus.business.service.AbstractService;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class AttendanceServiceImpl extends AbstractService implements AttendanceService {

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Override
  public Attendance create(long childId, Attendance attendance) {

    for (AttendancePeriod p : attendance.getPeriods()) {
      p.setAttendance(attendance);
    }

    return attendanceRepository.save(attendance);
  }

  @Override
  public Attendance get(long childId, LocalDate date) {
    return attendanceRepository.findByContractIdAndDate(childId, date);
  }

  @Override
  public List<Attendance> getBetween(long childId, LocalDate startPeriod, LocalDate endPeriod) {
    Collection<Attendance> attendances = attendanceRepository.findByContractIdAndDateBetween(childId, startPeriod, endPeriod);
    return Lists.newArrayList(attendances);
  }

  @Override
  public void delete(long attendanceId) {
    final Attendance attendance = attendanceRepository.findOne(attendanceId);
    //attendance.getPeriods().clear();
    attendanceRepository.delete(attendance);
  }
}
