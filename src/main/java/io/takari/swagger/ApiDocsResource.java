package io.takari.swagger;

import io.airlift.http.server.WebModule;
import io.takari.swagger.v12.ApiDeclaration;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/docs")
public class ApiDocsResource {

  public static final String API_BASE_PATH = "io.airlift.swagger.apiBasePath";
  
  private final Swagger swagger;

  @Inject
  public ApiDocsResource(WebModule.WebMapping mapping, @Named(API_BASE_PATH) String apiBasePath) {
    swagger = Swagger.builder() //
        .basePath("/api") //
        .jaxRsClasses(mapping.jaxrsResources()) //
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