package io.takari.swagger.v12;

public abstract class ArrayType implements DataType {

  @Override
  public final boolean isArray() {
    return true;
  }

  @Override
  public final boolean isComplex() {
    return false;
  }

  @Override
  public final boolean isVoid() {
    return false;
  }

}
