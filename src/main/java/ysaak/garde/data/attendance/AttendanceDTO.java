package ysaak.garde.data.attendance;

import ysaak.garde.data.contract.ContractDTO;

import java.time.LocalDate;
import java.util.List;

public class AttendanceDTO {

  private Long id;

  private ContractDTO contract;

  private LocalDate date;
  
  private MaintenanceFee maintenanceFee;
  
  private MealFee mealFee;

  private List<AttendancePeriodDTO> periods;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ContractDTO getContract() {
    return contract;
  }

  public void setContract(ContractDTO contract) {
    this.contract = contract;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public MaintenanceFee getMaintenanceFee() {
    return maintenanceFee;
  }

  public void setMaintenanceFee(MaintenanceFee maintenanceFee) {
    this.maintenanceFee = maintenanceFee;
  }

  public MealFee getMealFee() {
    return mealFee;
  }

  public void setMealFee(MealFee mealFee) {
    this.mealFee = mealFee;
  }

  public List<AttendancePeriodDTO> getPeriods() {
    return periods;
  }

  public void setPeriods(List<AttendancePeriodDTO> periods) {
    this.periods = periods;
  }

}
