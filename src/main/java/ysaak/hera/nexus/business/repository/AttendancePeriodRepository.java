package ysaak.hera.nexus.business.repository;

import org.springframework.data.repository.CrudRepository;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;


public interface AttendancePeriodRepository extends CrudRepository<AttendancePeriod, Long> {
}
