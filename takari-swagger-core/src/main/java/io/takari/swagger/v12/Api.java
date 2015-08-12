package io.takari.swagger.v12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Api {
  private String path;
  private String description;
  private final List<Operation> operations = new ArrayList<Operation>();

  
  public Api(String path) {
    setPath(path);
  }

  public Api(String path, String nickName, Operation.Method method) {
    setPath(path);
    //addOperation(nickName, method);
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    assert path != null && path.trim().length() > 0 : "path can not be null or empty";
    this.path = path;
  }

  public String getDescription() {
    return description == null ? "" : description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Operation getOperation(String nickName) {
    assert nickName != null : "nickName can not be null";
    synchronized (operations) {
      for (Operation operation : operations) {
        if (operation.getNickname().equals(nickName)) {
          return operation;
        }
      }
    }

    return null;
  }

  public List<Operation> getOperations() {
    return Collections.unmodifiableList(operations);
  }

  public void removeOperation(Operation operation) {
    assert operation != null && operation.getNickname() != null : "operation can not be null and must have a nickname";
    synchronized (operations) {
      operations.remove(operation);
    }
  }

  public Operation addOperation(String nickName, Operation.Method method, String description) {
    assert nickName != null && method != null : "operation can not be null and must have a nickname";
    assert description != null : "description can not be null";
    assert getOperation(nickName) == null : "operation with nickName [" + nickName + "] already exists";

    synchronized (operations) {
      Operation result = new Operation(nickName, method, description);
      operations.add(result);
      return result;
    }
  }
}
