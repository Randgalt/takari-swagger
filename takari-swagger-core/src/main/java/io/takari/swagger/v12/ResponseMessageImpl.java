package io.takari.swagger.v12;

public class ResponseMessageImpl implements ResponseMessage {

  private int code;
  private String message;
  private String responseModel;

  public ResponseMessageImpl(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public void setCode(int code) {
    assert code > 0 : "Error code can not be 0";
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String getResponseModel() {
    return responseModel;
  }

  @Override
  public void setResponseModel(String responseModel) {
    this.responseModel = responseModel;
  }
}
