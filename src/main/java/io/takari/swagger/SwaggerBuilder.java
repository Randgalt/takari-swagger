package io.takari.swagger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.google.common.collect.Maps;

import io.takari.swagger.v12.Api;
import io.takari.swagger.v12.ApiDeclaration;
import io.takari.swagger.v12.DataType;
import io.takari.swagger.v12.Model;
import io.takari.swagger.v12.Operation;
import io.takari.swagger.v12.Parameter;
import io.takari.swagger.v12.PrimitiveType;
import io.takari.swagger.v12.Property;
import io.takari.swagger.v12.RefDataType;
import io.takari.swagger.v12.ResourceListing;

//
// TODO: parameterize resourceBase, this needs to come from the server
// TODO: extra produces/consumes from the resources, do we need class level or does only method level matter?
//

public class SwaggerBuilder {

  // basePath for all the systems resources, where we mount Jersey or RESTEeasy
  //private final String basePath = "http://localhost:8080/api";
  //private final String basePath = "/nexus/service/siesta";
  
  private final String swaggerVersion = "1.2";
  private final String swaggerApiVersion = "1.0.0"; // I don't actually know what this means

  private String basePath;
  private Set<Class<? extends Object>> jaxRsClasses;
  
  public SwaggerBuilder basePath(String basePath) {
    this.basePath = basePath;
    return this;
  }
  
  public SwaggerBuilder jaxRsClasses(Set<Class<? extends Object>> jaxRsClasses) {
    this.jaxRsClasses = jaxRsClasses;
    return this;    
  }
  
  public Swagger build() {
    Map<String, ApiDeclaration> apis = Maps.newHashMap();
    ResourceListing resourceListing = new ResourceListing(swaggerVersion);
    resourceListing.setApiVersion(swaggerApiVersion);
    //
    // We know here that all these classes have a class-level JAXRS Path annotation
    //
    for (Class<?> clazz : jaxRsClasses) {
      //
      // We don't need to document the SwaggerResource itself
      //
      if (clazz.getName().equals(ApiDocsResource.class.getName())) {
        continue;
      }
      String resourcePath = clazz.getAnnotation(Path.class).value();
      ApiDeclaration apiDeclaration = new ApiDeclaration();
      apiDeclaration.setBasePath(basePath);
      apiDeclaration.setResourcePath(resourcePath);
      //
      // Look at the resource level produces and consumes
      //
      Produces producesAnno = clazz.getAnnotation(Produces.class);
      if (producesAnno != null) {
        String[] producesValues = producesAnno.value();
        for (String produces : producesValues) {
          apiDeclaration.addProduces(produces);
        }
      }
      Consumes consumesAnno = clazz.getAnnotation(Consumes.class);
      if (consumesAnno != null) {
        String[] consumesValues = consumesAnno.value();
        for (String consumes : consumesValues) {
          apiDeclaration.addConsumes(consumes);
        }
      }
      apiDeclaration.setSwaggerVersion(swaggerVersion);
      //
      // We need to normalize the key by which we store the ApiDeclaration. The swagger-ui makes a call
      // to build the UI by using the path of a resources. So if we have a resource with a path
      // ${baseUrl}/api/user then it will make a call to ${baseUrl}/swagger/user to find
      // the ApiDeclaration document.
      //
      String resourceId = stripLeadingSlashIfPresent(resourcePath).replace('{', '_').replace('}', '_').replace("/", "");
      resourceId = resourceId.substring(0,resourceId.length()-1);
      apis.put(resourceId, apiDeclaration);
      resourceListing.addApi(apiDeclaration, resourceId);

      for (Method method : clazz.getMethods()) {
        if (!isJaxrsMethod(method)) {
          continue;
        }
        Path pathAnnotation = method.getAnnotation(Path.class);
        String apiPath = apiPath(resourcePath, pathAnnotation);
        //
        // We pass in the apiDeclaration because we need to collect the swagger models
        //
        apiDeclaration.addApi(api(apiDeclaration, apiPath, method));
      }
    }
    
    return new Swagger(resourceListing, apis);
  }

  private String stripLeadingSlashIfPresent(String resourcePath) {
    if (resourcePath.startsWith("/")) {
      return resourcePath.substring(1);
    }
    return resourcePath;
  }

  private String apiPath(String resourcePath, Path methodPathAnno) {
    if (methodPathAnno != null) {
      String methodPath = methodPathAnno.value();
      if (!methodPath.startsWith("/")) {
        methodPath += "/";
      }
      return resourcePath + methodPath;
    } else {
      return resourcePath;
    }
  }

  private boolean isJaxrsMethod(Method method) {
    return (method.getAnnotation(POST.class) != null) || //
        (method.getAnnotation(GET.class) != null) || //
        (method.getAnnotation(PUT.class) != null) || //
        (method.getAnnotation(DELETE.class) != null);
  }

  private Api api(ApiDeclaration apiDeclaration, String apiPath, Method method) {
    Operation.Method swaggerMethod = null;
    if (method.getAnnotation(POST.class) != null) {
      swaggerMethod = Operation.Method.POST;
    } else if (method.getAnnotation(GET.class) != null) {
      swaggerMethod = Operation.Method.GET;
    } else if (method.getAnnotation(PUT.class) != null) {
      swaggerMethod = Operation.Method.PUT;
    } else if (method.getAnnotation(DELETE.class) != null) {
      swaggerMethod = Operation.Method.DELETE;
    }

    Api api = new Api(apiPath, method.getName(), swaggerMethod);
    //Operation operation = api.addOperation(apiPath, swaggerMethod);
    Operation operation = api.addOperation(method.getName(), swaggerMethod);
    Produces producesAnno = method.getAnnotation(Produces.class);
    if (producesAnno != null) {
      String[] producesValues = producesAnno.value();
      for (String produces : producesValues) {
        operation.addProduces(produces);
      }
    }
    Consumes consumesAnno = method.getAnnotation(Consumes.class);
    if (consumesAnno != null) {
      String[] consumesValues = consumesAnno.value();
      for (String consumes : consumesValues) {
        operation.addConsumes(consumes);
      }
    }
    //
    // Add information about annotations
    //
    Class<?>[] parameterTypes = method.getParameterTypes();
    String[] parameterNames = ReflectionHelper.extractParameterNames(method);
    Annotation[][] parameterAnnotionsArray = method.getParameterAnnotations();
    for (int i = 0; i < parameterTypes.length; i++) {
      Class<?> parameterType = parameterTypes[i];
      //
      // Walk through the annotations and look for JAXRS annotations
      //
      String parameterName = parameterNames[i];
      Annotation[] parameterAnnotations = parameterAnnotionsArray[i];
      Parameter.ParamType swaggerParamType = swaggerParameterType(parameterAnnotations);
      DataType parameterDataType = javaToSwaggerType(parameterType);
      if (swaggerParamType == null) {
        if (!parameterType.isPrimitive()) {
          Model model = swaggerModel(parameterType);
          if (model != null) {
            swaggerParamType = Parameter.ParamType.body;
            parameterDataType = new RefDataType(parameterType.getSimpleName());
            //
            // This is funky but required by the Swagger console. If the parameterName is not "body"
            // the JSON document will not be posted to the server
            //
            parameterName = "body";
            //apiDeclaration.addModel(model);
          }
        }
      } else {
        parameterDataType = javaToSwaggerType(parameterType);
      }
      operation.addParameter(parameterName, parameterDataType.getType(), swaggerParamType);
    }
    return api;
  }

  private Parameter.ParamType swaggerParameterType(Annotation[] parameterAnnotations) {
    Parameter.ParamType type = null;
    for (int j = 0; j < parameterAnnotations.length; j++) {
      Annotation parameterAnnotation = parameterAnnotations[j];
      if (parameterAnnotation.annotationType().equals(PathParam.class)) {
        type = Parameter.ParamType.path;
      } else if (parameterAnnotation.annotationType().equals(FormParam.class)) {
        type = Parameter.ParamType.form;
      } else if (parameterAnnotation.annotationType().equals(HeaderParam.class)) {
        type = Parameter.ParamType.header;
      } else if (parameterAnnotation.annotationType().equals(Context.class)) {
        type = Parameter.ParamType.body;
      }
    }
    return type;
  }

  //
  // Create a Swagger model from one of domain model classes
  //
  private Model swaggerModel(Class<?> type) {
    Map<String, Property> properties = Maps.newHashMap();
    Model model = new Model(type.getSimpleName(), "");
    BeanInfo beanInfo;
    try {
      beanInfo = Introspector.getBeanInfo(type);
    } catch (IntrospectionException e) {
      return null;
    }
    PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
    for (PropertyDescriptor pd : pds) {
      String propertyName = pd.getName();
      if (propertyName.equals("class")) {
        continue;
      }
      DataType dataType = javaToSwaggerType(pd.getPropertyType());
      Property property = new Property(propertyName, dataType.toString(), "", true);
      properties.put(property.getName(), property);
    }
    model.setProperties(properties);
    return model;
  }

  public static DataType javaToSwaggerType(Class<?> type) {
    DataType dataType = null;
    //
    // Primitive types
    //
    if (type.equals(Integer.class) || type.equals(int.class)) {
      dataType = PrimitiveType.INTEGER;
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      dataType = PrimitiveType.LONG;
    } else if (type.equals(Float.class) || type.equals(float.class)) {
      dataType = PrimitiveType.FLOAT;
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      dataType = PrimitiveType.DOUBLE;
    } else if (type.equals(String.class)) {
      dataType = PrimitiveType.STRING;
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      dataType = PrimitiveType.BOOLEAN;
    } else if (type.equals(Date.class)) {
      dataType = PrimitiveType.DATE;
    } else {
      // Complex type
      dataType = new RefDataType(type.getSimpleName());
    }
    return dataType;
  }

  public static void main(String[] args) {
    System.out.println(SwaggerBuilder.javaToSwaggerType(Integer.class));
  }
}
