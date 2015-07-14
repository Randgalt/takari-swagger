package io.takari.swagger;

import io.takari.swagger.v12.ApiDeclaration;
import io.takari.swagger.v12.ResourceListing;

import java.util.Map;

public class Swagger {

  private ResourceListing resourceListing;
  private Map<String,ApiDeclaration> apiDeclarations;
  
  public Swagger(ResourceListing resourceListing, Map<String, ApiDeclaration> apiDeclarations) {
    this.resourceListing = resourceListing;
    this.apiDeclarations = apiDeclarations;
  }

  public ResourceListing getResourceListing() {
    return resourceListing;
  }

  public Map<String, ApiDeclaration> getApiDeclarations() {
    return apiDeclarations;
  }
  
  public static SwaggerBuilder builder() {
    return new SwaggerBuilder();
  }
}
