package ysaak.hera.nexus.business.service.child;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import ysaak.hera.nexus.business.repository.ChildRepository;
import ysaak.hera.nexus.business.service.AbstractService;
import ysaak.hera.nexus.business.service.parameter.ParameterService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.data.parameter.Parameter;
import ysaak.hera.nexus.exception.DataValidationException;

@Service
public class ChildServiceImpl extends AbstractService implements ChildService {

  private static final String BDAY_THRESHOLD_PARAM = "BIRTH-DAY-THRESHOLD";
  
  @Autowired
  private ParameterService parameterService;
  
  @Autowired
  private ChildRepository childRepository;
  
  @Override
  public Child create(Child child) throws DataValidationException {
    validate(child);
    childRepository.save(child);
    return child;
  }

  @Override
  public Child get(long id) {
    return childRepository.findOne(id);
  }

  @Override
  public List<Child> listAll() {
    final Iterable<Child> children = childRepository.findAll();
    return Lists.newArrayList(children);
  }

  @Override
  public boolean hasBirthDayNearby() {
    final TemporalField weekOfYearField = WeekFields.of(Locale.getDefault()).weekOfYear(); 
    final int currWeek = LocalDate.now().get(weekOfYearField);
    int initWeek = currWeek;
    boolean multiYear = false;
    
    final Parameter thresholdParam = parameterService.get(BDAY_THRESHOLD_PARAM);
    if (thresholdParam != null)
      initWeek -= thresholdParam.getIntValue();
    else
      initWeek -= 3;
    
    if (initWeek < 0) {
      initWeek = 52 + initWeek;
      multiYear = true;
      System.err.println("multi year");
    }
    
    
    for (Child c : listAll()) {
      int cbdWeek = c.getBirthDate().get(weekOfYearField);
      
      if (multiYear) {
        if (cbdWeek <= currWeek || cbdWeek >= initWeek)
          return true;
      }
      else {
        if (initWeek <= cbdWeek && cbdWeek <= currWeek) {
          return true;
        }
      }
    }
    
    return false;
  }
}
