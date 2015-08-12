package io.takari.swagger.v12;

public final class RefDataType implements DataType {

  private final String ref;

  public RefDataType(String ref) {
    this.ref = ref;
  }

  @Override
  public String getType() {
    return ref;
  }

  @Override
  public String getRef() {
    return ref;
  }

  @Override
  public String getFormat() {
    return null;
  }

  @Override
  public String toString() {
    return getRef();
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
    return false;
  }

  @Override
  public boolean isVoid() {
    return false;
  }

  @Override
  public boolean isRef() {
    return true;
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
