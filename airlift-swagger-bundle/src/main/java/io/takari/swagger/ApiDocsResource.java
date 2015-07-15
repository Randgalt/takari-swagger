package io.takari.swagger;

import io.takari.swagger.v12.ApiDeclaration;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/docs")
public class ApiDocsResource {
  private final Swagger swagger;

  @Inject
  public ApiDocsResource(Swagger swagger) {
    this.swagger = swagger;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getServiceListing() throws IOException {
    return Response.ok().entity(swagger.getResourceListing()).build();
  }

  @GET
  @Path("/{id:.*}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getApiListing(@PathParam("id") String path) throws IOException {
    ApiDeclaration apiDeclaration = swagger.getApiDeclarations().get(path);
    return Response.ok().entity(apiDeclaration).build();
  }
}