package io.takari.swagger.v12;

public class Parameter {
  private String type;
  private ParamType paramType;
  private String name;
  private String description;
  private boolean required = true;
  private boolean allowMultiple;

  /**
   * Parameter type - see <a href="https://github.com/wordnik/swagger-core/wiki/Parameters"
   * target="_new">https://github.com/wordnik/swagger-core/wiki/Parameters</a>
   */

  public enum ParamType {
    path, query, body, header, form
  }

  Parameter(String name, String type, ParamType paramType) {
    this.name = name;
    this.type = type;
    this.paramType = paramType;
  }
  
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ParamType getParamType() {
    return paramType;
  }

  public void setParamType(ParamType paramType) {
    assert paramType != null : "paramType can not be null";

    this.paramType = paramType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    assert name != null : "parameter name can not be null";
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isRequired() {
    return paramType == ParamType.path || required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isAllowMultiple() {
    return allowMultiple;
  }

  public void setAllowMultiple(boolean multiple) {
    this.allowMultiple = multiple;
  }
}
