package ysaak.garde.business.model.parameter;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Parameter)) return false;


    Parameter other = (Parameter) obj;

    return Objects.equals(id, other.id)
            && Objects.equals(code, other.code)
            && Objects.equals(type, other.type)
            && Objects.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, value);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("code", code)
            .add("type", type)
            .add("value", value)
            .toString();
  }
}
