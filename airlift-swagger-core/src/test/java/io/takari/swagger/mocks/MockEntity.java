package io.takari.swagger.mocks;

import io.takari.swagger.annotations.Description;

@Description("This is an entity")
public class MockEntity {
  private String sValue;
  private int iValue;

  public MockEntity() {
    this("", 0);
  }

  public MockEntity(String sValue, int iValue) {
    this.sValue = sValue;
    this.iValue = iValue;
  }

  public String getsValue() {
    return sValue;
  }

  public void setsValue(String sValue) {
    this.sValue = sValue;
  }

  public int getiValue() {
    return iValue;
  }

  public void setiValue(int iValue) {
    this.iValue = iValue;
  }
}
