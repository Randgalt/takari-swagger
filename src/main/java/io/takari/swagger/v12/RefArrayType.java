package io.takari.swagger.v12;

public final class RefArrayType extends ArrayType {

  private final String ref;

  public RefArrayType(String ref) {
    this.ref = ref;
  }

  @Override
  public String getType() {
    return null;
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
    return new StringBuilder("array[").append(ref).append(']').toString();
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isRef() {
    return true;
  }

}
