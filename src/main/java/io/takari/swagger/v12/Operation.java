package io.takari.swagger.v12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operation {
  private Method method;
  private String nickname;
  private String summary;
  private String notes;
  private String responseClass;
  private String type = "void";
  private final Set<String> produces = new HashSet<String>();
  private final Set<String> consumes = new HashSet<String>();
  private final List<Parameter> parameters = new ArrayList<Parameter>();
  private final List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();

  public enum Method {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH
  }

  public Operation(String nickName, Method method) {
    this.nickname = nickName;
    this.method = method;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    assert method != null : "method can not be null";
    this.method = method;
  }

  public String getNickName() {
    return nickname;
  }

  public void setNickName(String nickName) {
    assert nickName != null : "nickName can not be null";
    this.nickname = nickName;
  }

  public String getResponseClass() {
    return responseClass == null ? "void" : responseClass;
  }

  public void setResponseClass(String responseClass) {
    this.responseClass = responseClass;
  }

  public String getSummary() {
    return summary == null ? "" : summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Collection<String> getProduces() {
    return Collections.unmodifiableCollection(produces);
  }

  public void removeProduces(String produces) {
    this.produces.remove(produces);
  }

  public void addProduces(String produces) {
    assert produces != null : "produces can not be null";

    this.produces.add(produces);
  }

  public Collection<String> getConsumes() {
    return Collections.unmodifiableCollection(consumes);
  }

  public void removeConsumes(String consumes) {
    this.produces.remove(consumes);
  }

  public void addConsumes(String consumes) {
    assert consumes != null : "consumes can not be null";
    this.consumes.add(consumes);
  }

  public List<Parameter> getParameters() {
    return Collections.unmodifiableList(parameters);
  }

  public Parameter getParameter(String name) {
    assert name != null : "parameter name can not be null";

    synchronized (parameters) {
      for (Parameter parameter : parameters) {
        if (parameter.getName().equals(name)) {
          return parameter;
        }
      }

      return null;
    }
  }

  public void removeParameter(Parameter parameter) {
    assert parameter != null : "parameter can not be null";

    synchronized (parameters) {
      parameters.remove(parameter);
    }
  }

  public Parameter addParameter(String name, String type, Parameter.ParamType parameterType) {
    assert type != null : "parameter must be created with type";

    if (parameterType != Parameter.ParamType.body) {
      assert name != null : "parameter that is not a body must have a name";
      assert getParameter(name) == null : "Parameter already exists with name [" + name + "]";
    }

    synchronized (parameters) {
      Parameter parameter = new Parameter(name, type, parameterType);
      parameters.add(parameter);

      return parameter;
    }
  }

  public List<ResponseMessage> getResponseMessages() {
    return Collections.unmodifiableList(responseMessages);
  }

  public ResponseMessage getResponseMessage(int code) {
    assert code > 0 : "code can not be 0";

    synchronized (responseMessages) {
      for (ResponseMessage responseMessage : responseMessages) {
        if (responseMessage.getCode() == code) {
          return responseMessage;
        }
      }

      return null;
    }
  }

  public void removeResponseMessage(ResponseMessage responseMessage) {
    assert responseMessage != null;

    synchronized (responseMessages) {
      responseMessages.remove(responseMessage);
    }
  }

  public ResponseMessage addResponseMessage(int code, String message) {
    assert code > 0 : "code must have a value";
    assert getResponseMessage(code) == null : "Response for already exists for code [" + code + "]";

    synchronized (responseMessages) {
      ResponseMessage responseMessage = new ResponseMessageImpl(code, message);
      responseMessages.add(responseMessage);
      return responseMessage;
    }
  }
}
