package ysaak.garde.data.monthreport;

import java.time.Duration;

/**
 * Created by ysaak on 22/02/2017.
 */
public class WeekSummary {
  private final Duration totalHours;
  private final Duration monthHours;

  private final Duration complementHours;
  private final Duration increasedHours;

  private boolean countedInCurrentMonth = true;

  public WeekSummary(Duration totalHours, Duration monthHours, Duration complementHours, Duration increasedHours) {
    this.totalHours = totalHours;
    this.monthHours = monthHours;
    this.complementHours = complementHours;
    this.increasedHours = increasedHours;
  }

  public Duration getTotalHours() {
    return totalHours;
  }

  public Duration getMonthHours() {
    return monthHours;
  }

  public Duration getComplementHours() {
    return complementHours;
  }

  public Duration getIncreasedHours() {
    return increasedHours;
  }

  public boolean isCountedInCurrentMonth() {
    return countedInCurrentMonth;
  }

  public void setCountedInCurrentMonth(boolean countedInCurrentMonth) {
    this.countedInCurrentMonth = countedInCurrentMonth;
  }
}
