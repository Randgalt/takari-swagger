package io.takari.swagger.v12;

import java.util.List;

public interface HasDataType {

  String getName();

  DataType getDataType();

  Object getDefaultValue();

  List<String> getEnumValues();

  Number getMinimum();

  Number getMaximum();

}
