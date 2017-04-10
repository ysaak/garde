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
public class AttendanceServiceImpl extends AbstractService<Attendance> implements AttendanceService {

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Override
  public Attendance save(long childId, Attendance attendance) {

    if (attendance.getContractId() != null) {
      attendance.setContractId(childId);
    }

    // Store validation
    for (AttendancePeriod p : attendance.getPeriods()) {
      p.setAttendance(attendance);
    }

    return attendanceRepository.save(attendance);
  }

  @Override
  public Attendance get(long id) {
    return attendanceRepository.findOne(id);
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
    attendanceRepository.delete(attendance);
  }
}
