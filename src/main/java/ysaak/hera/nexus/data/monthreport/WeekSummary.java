package ysaak.hera.nexus.data.monthreport;

import lombok.Data;

import java.time.Duration;

/**
 * Created by ysaak on 22/02/2017.
 */
@Data
public class WeekSummary {
  private final Duration totalHours;
  private final Duration monthHours;

  private final Duration complementHours;
  private final Duration increasedHours;

  private boolean countedInCurrentMonth = true;
}
