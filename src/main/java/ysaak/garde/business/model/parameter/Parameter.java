package ysaak.garde.business.model.parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Parameter {
  /**
   * Parameter ID
   */
  private Long id;

  /**
   * Code
   */
  @NotNull
  private String code;

  /**
   * Type
   */
  @NotNull
  private ParameterType type;

  /**
   * Value
   */
  @NotNull
  private String value;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(unique = true, nullable = false)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Column(nullable = false, length = 10)
  public ParameterType getType() {
    return type;
  }

  public void setType(ParameterType type) {
    this.type = type;
  }

  @Column(nullable = false)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getIntValue() {
    return value != null ? Integer.parseInt(value) : null;
  }

  public void setIntValue(Integer value) {
    this.value = value != null ? String.valueOf(value) : null;
  }

  public Double getDoubleValue() {
    return value != null ? Double.parseDouble(value) : null;
  }

  public void setDoubleValue(Double value) {
    this.value = value != null ? String.valueOf(value) : null;
  }
}
