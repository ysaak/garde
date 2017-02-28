package ysaak.hera.nexus.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.business.service.attendance.AttendanceService;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.business.utils.NameGenerator;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.data.attendance.MaintenanceFee;
import ysaak.hera.nexus.data.attendance.MealFee;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockInitializer {
  
  @Autowired
  private ChildService childService;

  @Autowired
  private AttendanceService attendanceService;

  public void init() {
    final NameGenerator ng = new NameGenerator();

    Child baseChild = null;

    for (int i = 0; i < 10; i++) {
      Child a = new Child();
      a.setFirstName(ng.getName());
      a.setLastName((i==0 ? "XX " : "") + ng.getName().toUpperCase());
      a.setBirthDate(getRandomTimeBetweenTwoDates());
      a = childService.create(a);

      if (i == 0) {
        baseChild = a;
      }
    }

    if (baseChild == null) {
      throw new RuntimeException("No child created");
    }

    initAttendance(baseChild);
  }

  private void initAttendance(Child child) {
    AttendancePeriod p1 = new AttendancePeriod();
    p1.setStartHour(LocalTime.of(8, 0));
    p1.setEndHour(LocalTime.of(11, 30));

    AttendancePeriod p2 = new AttendancePeriod();
    p2.setStartHour(LocalTime.of(13, 0));
    p2.setEndHour(LocalTime.of(16, 00));

    AttendancePeriod p3 = new AttendancePeriod();
    p3.setStartHour(LocalTime.of(17, 45));
    p3.setEndHour(LocalTime.of(19, 00));

    List<AttendancePeriod> periods = Arrays.asList(p1, p2, p3);


    int nbAttendance = 120;
    LocalDate currentDate = LocalDate.of(2017, 1, 9);

    for (int i=0; i < nbAttendance; i++) {
      if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY && currentDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
        Attendance a1 = new Attendance();
        a1.setContractId(child.getId());
        a1.setDate(currentDate);
        a1.setMaintenanceFee(MaintenanceFee.YES);
        a1.setMealFee(MealFee.FULL);
        a1.setPeriods(new ArrayList<>());

        int nbPeriods = ThreadLocalRandom.current().nextInt(1, 4);
        for (int j = 0; j < nbPeriods; j++) {
          AttendancePeriod p = new AttendancePeriod();
          p.setStartHour(periods.get(j).getStartHour());
          p.setEndHour(periods.get(j).getEndHour());

          a1.getPeriods().add(p);
        }

        // Maintenance fee
        int nb = ThreadLocalRandom.current().nextInt(0, MaintenanceFee.values().length);
        a1.setMaintenanceFee(MaintenanceFee.values()[nb]);

        nb = ThreadLocalRandom.current().nextInt(0, MealFee.values().length);
        a1.setMealFee(MealFee.values()[nb]);

        attendanceService.create(child.getId(), a1);
      }

      currentDate = currentDate.plusDays(1);
    }
  }
  
  private LocalDate getRandomTimeBetweenTwoDates() {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    return LocalDate.ofEpochDay(randomDay);
  }
}
