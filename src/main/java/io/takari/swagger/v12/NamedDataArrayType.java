package io.takari.swagger.v12;

public final class NamedDataArrayType extends ArrayType {

  private final String name;

  public NamedDataArrayType(String name) {
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
    return new StringBuilder("array[").append(name).append(']').toString();
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isRef() {
    return false;
  }
}
