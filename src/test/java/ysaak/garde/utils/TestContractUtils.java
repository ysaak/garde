package ysaak.garde.utils;


import org.junit.Assert;
import org.junit.Test;
import ysaak.garde.business.utils.ContractUtils;

public class TestContractUtils {

  @Test
  public void testMonthlyPaymentCalculation() {
    final double hourPrice = 3.5;
    final double hoursPerWeek = 40;
    final int attendanceWeeks = 37;

    double monthlyPayment = ContractUtils.calculateMonthlyPayment(hourPrice, hoursPerWeek, attendanceWeeks);
    Assert.assertEquals(431.67, monthlyPayment, 0);
  }

  @Test
  public void testNormalMonthlyHoursCalculation() {
    final double hoursPerWeek = 42;
    final int attendanceWeeks = 37;

    int hours = ContractUtils.calculateNormalMonthlyHours(hoursPerWeek, attendanceWeeks);

    Assert.assertEquals(130, hours);
  }
}
