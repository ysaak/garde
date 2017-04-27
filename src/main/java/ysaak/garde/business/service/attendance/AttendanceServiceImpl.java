package ysaak.garde.business.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.model.attendance.Attendance;
import ysaak.garde.business.model.attendance.AttendancePeriod;
import ysaak.garde.business.repository.AttendanceRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.data.attendance.AttendanceDTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class AttendanceServiceImpl extends AbstractService<Attendance> implements AttendanceService {

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Override
  public AttendanceDTO save(AttendanceDTO attendance) {

    Attendance model = toModel(attendance, AttendanceDTO.class);

    // Store attendance in lines
    for (AttendancePeriod period : model.getPeriods()) {
      period.setAttendance(model);
    }

    model = attendanceRepository.save(model);

    return toDto(model, AttendanceDTO.class);
  }

  @Override
  public AttendanceDTO get(long id) {
    Attendance attendance = attendanceRepository.findOne(id);
    return toDto(attendance, AttendanceDTO.class);
  }

  @Override
  public AttendanceDTO get(long childId, LocalDate date) {
    Attendance attendance = attendanceRepository.findByContractIdAndDate(childId, date);
    return toDto(attendance, AttendanceDTO.class);
  }

  @Override
  public List<AttendanceDTO> getBetween(long childId, LocalDate startPeriod, LocalDate endPeriod) {
    Collection<Attendance> attendances = attendanceRepository.findByContractIdAndDateBetween(childId, startPeriod, endPeriod);
    return toDto(attendances, AttendanceDTO.class);
  }

  @Override
  public void delete(long attendanceId) {
    final Attendance attendance = attendanceRepository.findOne(attendanceId);
    attendanceRepository.delete(attendance);
  }
}
