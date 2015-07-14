package io.takari.swagger.v12;

import java.util.List;

public final class Property {

  private String name;
  private String type;
  private String description;
  private boolean required;
  private Object defaultValue;
  private List<String> enumValues;
  private Number minimum;
  private Number maximum;

  public Property(String name, String dataType, String description, boolean required) {
    this.name = name;
    this.type = dataType;
    this.description = description;
    this.required = required;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String dataType) {
    this.type = dataType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  public List<String> getEnumValues() {
    return enumValues;
  }

  public void setEnumValues(List<String> enumValues) {
    this.enumValues = enumValues;
  }

  public Number getMinimum() {
    return minimum;
  }

  public void setMinimum(Number minimum) {
    this.minimum = minimum;
  }

  public Number getMaximum() {
    return maximum;
  }

  public void setMaximum(Number maximum) {
    this.maximum = maximum;
  }
}
