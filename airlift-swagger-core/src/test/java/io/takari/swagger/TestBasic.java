package io.takari.swagger;

import com.google.common.collect.Sets;
import io.takari.swagger.mocks.MockResource;
import io.takari.swagger.v12.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestBasic {
  @Test
  public void testBasic() {
    // TODO tons more

    Swagger build = new SwaggerBuilder().basePath("/").jaxRsClasses(Sets.<Class<?>>newHashSet(MockResource.class)).build();
    ResourceListing resourceListing = build.getResourceListing();
    assertEquals(1, resourceListing.getApis().size());
    assertEquals("This is a mock resource", resourceListing.getApis().get(0).getDescription());

    Map<String, ApiDeclaration> apiDeclarations = build.getApiDeclarations();
    assertEquals(1, apiDeclarations.size());
    ApiDeclaration declaration = apiDeclarations.get("test/resource");
    Assert.assertNotNull(declaration);
    List<Api> apis = declaration.getApis();
    assertEquals(2, apis.size());
    Api api = apis.get(0);
    assertEquals("/test/resource/api", api.getPath());
    List<Operation> operations = api.getOperations();
    assertEquals(1, operations.size());
    Operation operation = operations.get(0);
    assertEquals("get test", operation.getSummary());

    api = apis.get(1);
    assertEquals("/test/resource/api2", api.getPath());
    operations = api.getOperations();
    assertEquals(1, operations.size());
    operation = operations.get(0);
    assertEquals("get another test", operation.getSummary());
    List<Parameter> parameters = operation.getParameters();
    assertEquals(1, parameters.size());
    Parameter parameter = parameters.get(0);
    assertEquals("parameter test", parameter.getDescription());
  }
}
