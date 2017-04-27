package ysaak.garde.business.model.attendance;

import ysaak.garde.business.model.contract.Contract;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Attendance {

  private Long id;

  private Contract contract;

  private LocalDate date;

  private MaintenanceFee maintenanceFee;
  
  private MealFee mealFee;

  private List<AttendancePeriod> periods;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contract_id", nullable = false)
  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  @Column(nullable = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Column(nullable = false, length = 3)
  public MaintenanceFee getMaintenanceFee() {
    return maintenanceFee;
  }

  public void setMaintenanceFee(MaintenanceFee maintenanceFee) {
    this.maintenanceFee = maintenanceFee;
  }

  @Column(nullable = false, length = 3)
  public MealFee getMealFee() {
    return mealFee;
  }

  public void setMealFee(MealFee mealFee) {
    this.mealFee = mealFee;
  }

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "attendance")
  public List<AttendancePeriod> getPeriods() {
    return periods;
  }

  public void setPeriods(List<AttendancePeriod> periods) {
    this.periods = periods;
  }
}
