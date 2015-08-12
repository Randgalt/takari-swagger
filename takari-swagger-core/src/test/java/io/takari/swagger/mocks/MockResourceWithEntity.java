package io.takari.swagger.mocks;

import io.takari.swagger.annotations.Description;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/test")
public class MockResourceWithEntity {
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("api")
  public Response getTest(MockEntity mockEntity) {
    return Response.ok().build();
  }
}
