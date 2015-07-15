package io.takari.swagger;

import com.google.common.collect.Sets;
import io.takari.swagger.mocks.MockResource;
import io.takari.swagger.mocks.MockResourceWithEntity;
import io.takari.swagger.v12.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
    assertNotNull(declaration);
    List<Api> apis = declaration.getApis();
    assertEquals(2, apis.size());
    for (Api api : apis) {
      if (api.getPath().equals("/test/resource/api")) {
        List<Operation> operations = api.getOperations();
        assertEquals(1, operations.size());
        Operation operation = operations.get(0);
        assertEquals("get test", operation.getSummary());
      } else if (api.getPath().equals("/test/resource/api2")) {
        List<Operation> operations = api.getOperations();
        assertEquals(1, operations.size());
        Operation operation = operations.get(0);
        assertEquals("get another test", operation.getSummary());
        List<Parameter> parameters = operation.getParameters();
        assertEquals(1, parameters.size());
        Parameter parameter = parameters.get(0);
        assertEquals("parameter test", parameter.getDescription());
      } else {
        fail("Unexpected path: " + api.getPath());
      }
    }
  }

  @Test
  public void testEntity() {
    Swagger build = new SwaggerBuilder().basePath("/").jaxRsClasses(Sets.<Class<?>>newHashSet(MockResourceWithEntity.class)).build();
    ResourceListing resourceListing = build.getResourceListing();
    assertEquals(1, resourceListing.getApis().size());
    Map<String, ApiDeclaration> apiDeclarations = build.getApiDeclarations();
    assertEquals(1, apiDeclarations.size());

    ApiDeclaration apiDeclaration = apiDeclarations.values().iterator().next();
    Map<String, Model> models = apiDeclaration.getModels();
    assertEquals(1, models.size());
    Model model = models.values().iterator().next();
    assertEquals("This is an entity", model.getDescription());
    assertEquals("MockEntity", model.getName());
    Map<String, Property> properties = model.getProperties();
    Property sValue = properties.get("sValue");
    Property iValue = properties.get("iValue");
    assertNotNull(sValue);
    assertNotNull(iValue);
    assertEquals("string", sValue.getType());
    assertEquals("integer", iValue.getType());
  }
}
