package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import ysaak.garde.business.model.attendance.Attendance;

import java.time.LocalDate;
import java.util.Collection;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

  Attendance findByContractIdAndDate(Long contractId, LocalDate date);

  Collection<Attendance> findByContractIdAndDateBetween(Long contractId, LocalDate start, LocalDate end);
}
