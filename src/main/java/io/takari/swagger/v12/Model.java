package io.takari.swagger.v12;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Model {

  private String id;
  private String description;
  private List<String> requiredProperties;
  private Map<String,Property> properties;

  public Model(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getRequiredProperties() {
    return requiredProperties;
  }

  public void setRequiredProperties(List<String> requiredProperties) {
    this.requiredProperties = requiredProperties;
  }

  public Map<String,Property> getProperties() {
    return properties;
  }

  public void setProperties(Map<String,Property> properties) {
    this.properties = properties;
  }

  public String getName() {
    return getId();
  }

  public List<String> getEnumValues() {
    return Collections.emptyList();
  }

  public Object getDefaultValue() {
    return null;
  }

  public Number getMinimum() {
    return null;
  }

  public Number getMaximum() {
    return null;
  }

}
