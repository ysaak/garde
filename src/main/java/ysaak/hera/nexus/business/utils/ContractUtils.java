package ysaak.hera.nexus.business.utils;

import ysaak.hera.nexus.data.contract.Contract;

public class ContractUtils {
  public static double calculateMensuality(Contract contract) {
    
    
    
    
    
    double horaireMensuelMoyen = 11.;
    double salaireMensuelMoyen = 11.;
    
    horaireMensuelMoyen = contract.getAttendancePerWeek() * contract.getHoursPerDay() * contract.getWeekPerYear() / 12.;
    
    salaireMensuelMoyen = horaireMensuelMoyen * contract.getHourPrice();
    
    return salaireMensuelMoyen;
  }
}
