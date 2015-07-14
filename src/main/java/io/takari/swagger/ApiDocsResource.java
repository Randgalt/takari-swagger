package io.takari.swagger;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.takari.swagger.v12.ApiDeclaration;

@Path("/docs")
public class ApiDocsResource {

  public static final String API_BASE_PATH = "io.airlift.swagger.apiBasePath";
  
  private final Swagger swagger;

  @Inject
  public ApiDocsResource(Set<Class<? extends Object>> jaxrsResources , @Named(API_BASE_PATH) String apiBasePath) {
    swagger = Swagger.builder() //
        .basePath("/api") //
        .jaxRsClasses(jaxrsResources) //
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getServiceListing() throws IOException {
    return Response.ok().entity(swagger.getResourceListing()).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getApiListing(@PathParam("id") String path) throws IOException {
    ApiDeclaration apiDeclaration = swagger.getApiDeclarations().get(path); 
    return Response.ok().entity(apiDeclaration).build();
  }
}