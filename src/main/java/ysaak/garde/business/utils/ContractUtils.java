package ysaak.garde.business.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilities for contracts
 */
public final class ContractUtils {

  /**
   * Hidden constructor
   */
  private ContractUtils() {}

  /**
   * Calculate the monthly payment
   * @param hourPrice Hour price
   * @param hoursPerWeek Hours per week
   * @param attendanceWeek Attendance weeks
   * @return Monthly payment
   */
  public static double calculateMonthlyPayment(double hourPrice, double hoursPerWeek, int attendanceWeek) {
    double payment = hourPrice * hoursPerWeek * attendanceWeek / 12.;

    final BigDecimal bd = new BigDecimal(payment).setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  /**
   * Calculate normal monthly hours
   * @param hoursPerWeek Hours per week
   * @param attendanceWeek Attendance weeks
   * @return Normal monthly hours
   */
  public static int calculateNormalMonthlyHours(double hoursPerWeek, int attendanceWeek) {
    double hours = hoursPerWeek * attendanceWeek / 12.;

    final BigDecimal bd = new BigDecimal(hours).setScale(0, RoundingMode.HALF_UP);
    return bd.intValueExact();
  }
}
