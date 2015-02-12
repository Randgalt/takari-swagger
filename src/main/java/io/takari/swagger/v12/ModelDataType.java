package io.takari.swagger.v12;

public final class ModelDataType implements DataType {

  private final String name;

  public ModelDataType(String name) {
    this.name = name;
  }

  @Override
  public String getType() {
    return name;
  }

  @Override
  public String getRef() {
    return null;
  }

  @Override
  public String getFormat() {
    return null;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isComplex() {
    return true;
  }

  @Override
  public boolean isVoid() {
    return false;
  }

  @Override
  public boolean isRef() {
    return false;
  }

  public boolean isInteger() {
    return false;
  }

  public boolean isLong() {
    return false;
  }

  public boolean isFloat() {
    return false;
  }

  public boolean isDouble() {
    return false;
  }

  public boolean isString() {
    return false;
  }

  public boolean isByte() {
    return false;
  }

  public boolean isBoolean() {
    return false;
  }

  public boolean isDate() {
    return false;
  }

  public boolean isDateTime() {
    return false;
  }

}
