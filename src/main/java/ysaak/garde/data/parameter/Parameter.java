package ysaak.garde.data.parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Parameter {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(unique = true, nullable = false)
  private Long id;
  
  @Column(unique = true, nullable = false)
  @NotNull
  private String code;
  
  @Column(nullable = false)
  @NotNull
  private ParameterType type;
  
  @Column(nullable = false)
  @NotNull
  private String value;

  public String getStringValue() {
    return value;
  }
  
  public Integer getIntValue() {
    return value != null ? Integer.parseInt(value) : null;
  }
  
  public Double getDoubleValue() {
    return value != null ? Double.parseDouble(value) : null;
  }
}
