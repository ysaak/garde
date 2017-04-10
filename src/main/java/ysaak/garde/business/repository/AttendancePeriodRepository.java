package ysaak.garde.business.repository;

import org.springframework.data.repository.CrudRepository;
import ysaak.garde.data.attendance.AttendancePeriod;


public interface AttendancePeriodRepository extends CrudRepository<AttendancePeriod, Long> {
}
