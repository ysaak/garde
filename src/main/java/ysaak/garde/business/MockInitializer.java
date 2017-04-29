package ysaak.garde.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ysaak.garde.business.service.attendance.AttendanceService;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.business.service.contract.ContractService;
import ysaak.garde.business.utils.NameGenerator;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.data.attendance.AttendancePeriodDTO;
import ysaak.garde.data.attendance.MaintenanceFee;
import ysaak.garde.data.attendance.MealFee;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.data.contract.ContractType;
import ysaak.garde.exception.validation.ValidationException;

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
  private ContractService contractService;

  @Autowired
  private AttendanceService attendanceService;

  public void init() {

    List<ChildDTO> children = childService.listAll();
    if (children.size() > 0) {
      return;
    }


    final NameGenerator ng = new NameGenerator();

    ChildDTO baseChild = null;
    ChildDTO base2Child = null;

    try {
      for (int i = 0; i < 10; i++) {
        ChildDTO a = new ChildDTO();
        a.setFirstName(ng.getName());
        a.setLastName((i==0 ? "XX " : (i==1 ? "YY " : "")) + ng.getName().toUpperCase());
        a.setBirthDate(getRandomTimeBetweenTwoDates());
        a = childService.save(a);

        if (i == 0) {
          baseChild = a;
        }
        else if (i==1) {
          base2Child = a;
        }
      }

      if (baseChild == null || base2Child == null) {
        throw new RuntimeException("No child created");
      }

      ContractDTO contract = initContract(baseChild, false);
      initAttendance(contract);

      initContract(base2Child, true);
    }
    catch (ValidationException v) {
      v.printStackTrace();
    }
  }

  private ContractDTO initContract(ChildDTO child, boolean ended) throws ValidationException {

    ContractDTO contract = new ContractDTO();
    contract.setChild(child);
    contract.setStartDate(LocalDate.of(2017, 1, 1));

    if (ended) {
      contract.setEndDate(LocalDate.of(2017, 1, 31));
    }

    contract.setType(ContractType.PARTIAL);
    contract.setWeekPerYear(37);
    contract.setAttendancePerWeek(4);
    contract.setHoursPerWeek(30.);
    contract.setBaseHourPrice(3.5);
    contract.setIncreasedHourValue(5.);

    return contractService.create(contract);
  }

  private void initAttendance(ContractDTO contract) {
    AttendancePeriodDTO p1 = new AttendancePeriodDTO();
    p1.setStartHour(LocalTime.of(8, 0));
    p1.setEndHour(LocalTime.of(11, 30));

    AttendancePeriodDTO p2 = new AttendancePeriodDTO();
    p2.setStartHour(LocalTime.of(13, 0));
    p2.setEndHour(LocalTime.of(16, 0));

    AttendancePeriodDTO p3 = new AttendancePeriodDTO();
    p3.setStartHour(LocalTime.of(17, 45));
    p3.setEndHour(LocalTime.of(19, 0));

    List<AttendancePeriodDTO> periods = Arrays.asList(p1, p2, p3);


    int nbAttendance = 120;
    LocalDate currentDate = LocalDate.of(2017, 1, 9);

    for (int i=0; i < nbAttendance; i++) {
      if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY && currentDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
        AttendanceDTO a1 = new AttendanceDTO();
        a1.setContract(contract);
        a1.setDate(currentDate);
        a1.setMaintenanceFee(MaintenanceFee.YES);
        a1.setMealFee(MealFee.FULL);
        a1.setPeriods(new ArrayList<>());

        int nbPeriods = ThreadLocalRandom.current().nextInt(1, 4);
        for (int j = 0; j < nbPeriods; j++) {
          AttendancePeriodDTO p = new AttendancePeriodDTO();
          p.setStartHour(periods.get(j).getStartHour());
          p.setEndHour(periods.get(j).getEndHour());

          a1.getPeriods().add(p);
        }

        // Maintenance fee
        int nb = ThreadLocalRandom.current().nextInt(0, MaintenanceFee.values().length);
        a1.setMaintenanceFee(MaintenanceFee.values()[nb]);

        nb = ThreadLocalRandom.current().nextInt(0, MealFee.values().length);
        a1.setMealFee(MealFee.values()[nb]);

        attendanceService.save(a1);
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
