package ysaak.garde.data.parameter;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class ParameterDTO {
  private String code;
  
  private ParameterType type;
  
  private String value;

  public ParameterDTO() { /* empty constructor */ }

  public ParameterDTO(String code, ParameterType type, String value) {
    this.code = code;
    this.type = type;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ParameterType getType() {
    return type;
  }

  public void setType(ParameterType type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }
  
  public Integer getIntValue() {
    return value != null ? Integer.parseInt(value) : null;
  }
  
  public Double getDoubleValue() {
    return value != null ? Double.parseDouble(value) : null;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof ParameterDTO)) return false;


    ParameterDTO other = (ParameterDTO) obj;

    return Objects.equals(code, other.code)
            && Objects.equals(type, other.type)
            && Objects.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, type, value);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("code", code)
            .add("type", type)
            .add("value", value)
            .toString();
  }
}
