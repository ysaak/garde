package ysaak.garde.data.parameter;

public class ParameterDTO {
  private Long id;
  
  private String code;
  
  private ParameterType type;
  
  private String value;

  public ParameterDTO() { /* empty constructor */ }

  public ParameterDTO(String code, ParameterType type, String value) {
    this.code = code;
    this.type = type;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
