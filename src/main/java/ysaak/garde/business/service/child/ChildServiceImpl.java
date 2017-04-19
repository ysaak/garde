package ysaak.garde.business.service.child;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.garde.business.model.Child;
import ysaak.garde.business.repository.ChildRepository;
import ysaak.garde.business.service.AbstractService;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.parameter.Parameter;
import ysaak.garde.exception.validation.ValidationException;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class ChildServiceImpl extends AbstractService<Child> implements ChildService {

  private static final String BIRTHDAY_THRESHOLD_PARAM = "BIRTH-DAY-THRESHOLD";
  
  @Autowired
  private ParameterService parameterService;
  
  @Autowired
  private ChildRepository childRepository;

  @Override
  public ChildDTO save(ChildDTO child) throws ValidationException {
    // Convert
    Child model = mapTo(child, Child.class);

    // Validate
    validate(model);

    // Store data
    model = childRepository.save(model);

    return mapTo(model, ChildDTO.class);
  }

  @Override
  public ChildDTO get(long id) {
    return mapTo(childRepository.findOne(id), ChildDTO.class);
  }

  @Override
  public List<ChildDTO> listAll() {
    final Iterable<Child> children = childRepository.findAll();

    return mapTo(children, ChildDTO.class);
  }

  @Override
  public boolean hasBirthDayNearby() {
    final TemporalField weekOfYearField = WeekFields.of(Locale.getDefault()).weekOfYear(); 
    final int currWeek = LocalDate.now().get(weekOfYearField);
    int initWeek = currWeek;
    boolean multiYear = false;
    
    final Parameter thresholdParam = parameterService.get(BIRTHDAY_THRESHOLD_PARAM);
    if (thresholdParam != null)
      initWeek -= thresholdParam.getIntValue();
    else
      initWeek -= 3;
    
    if (initWeek < 0) {
      initWeek = 52 + initWeek;
      multiYear = true;
      System.err.println("multi year");
    }
    
    
    for (ChildDTO c : listAll()) {
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
