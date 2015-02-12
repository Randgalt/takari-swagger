package io.takari.swagger.v12;

public interface ResponseMessage {

  public int getCode();

  public void setCode(int code);

  public String getMessage();

  public void setMessage(String message);

  public String getResponseModel();

  public void setResponseModel(String responseModel);

}
