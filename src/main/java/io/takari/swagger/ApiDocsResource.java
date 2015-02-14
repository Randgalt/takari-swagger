package io.takari.swagger;

import io.takari.swagger.v12.ApiDeclaration;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/docs")
public class ApiDocsResource {

  private Swagger swagger;

  @Inject
  public ApiDocsResource(List<Class<? extends Object>> jaxrsClasses) {    
    swagger = Swagger.builder() //
        .basePath("/nexus/service/siesta") //
        .jaxRsClasses(jaxrsClasses) //
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