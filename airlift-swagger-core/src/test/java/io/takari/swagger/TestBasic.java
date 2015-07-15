package io.takari.swagger;

import com.google.common.collect.Sets;
import io.takari.swagger.mocks.MockResource;
import io.takari.swagger.v12.ResourceListing;
import org.junit.Test;

public class TestBasic {
  @Test
  public void testBasic() {
    Swagger build = new SwaggerBuilder().basePath("/").jaxRsClasses(Sets.<Class<?>>newHashSet(MockResource.class)).build();
    ResourceListing resourceListing = build.getResourceListing();
    System.out.println(resourceListing);
  }
}
