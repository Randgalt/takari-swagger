package io.takari.swagger.v12;

public final class PrimitiveArrayType extends ArrayType {

  private final PrimitiveType itemsType;

  public PrimitiveArrayType(PrimitiveType itemsType) {
    this.itemsType = itemsType;
  }

  @Override
  public String getType() {
    return itemsType.getType();
  }

  @Override
  public String getRef() {
    return null;
  }

  @Override
  public String getFormat() {
    return itemsType.getFormat();
  }

  @Override
  public String toString() {
    return new StringBuilder("array[").append(itemsType).append(']').toString();
  }

  @Override
  public boolean isPrimitive() {
    return true;
  }

  @Override
  public boolean isRef() {
    return false;
  }

  public boolean isInteger() {
    return itemsType.isInteger();
  }

  public boolean isLong() {
    return itemsType.isLong();
  }

  public boolean isFloat() {
    return itemsType.isFloat();
  }

  public boolean isDouble() {
    return itemsType.isDouble();
  }

  public boolean isString() {
    return itemsType.isString();
  }

  public boolean isByte() {
    return itemsType.isByte();
  }

  public boolean isBoolean() {
    return itemsType.isBoolean();
  }

  public boolean isDate() {
    return itemsType.isDate();
  }

  public boolean isDateTime() {
    return itemsType.isDateTime();
  }

}
