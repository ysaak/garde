package ysaak.garde.data.contract;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Contract {
  private Long id;
  
  private LocalDate startDate;
  
  private LocalDate endDate;
  
  private ContractType type;
  
  private int weekPerYear;
  
  private int attendancePerWeek;
  
  private double hoursPerDay;
  
  private double hourPrice;

  private boolean workOnSaturday;
}
