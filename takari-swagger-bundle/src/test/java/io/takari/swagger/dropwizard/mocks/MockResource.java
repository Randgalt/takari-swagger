package io.takari.swagger.dropwizard.mocks;

import io.takari.swagger.annotations.Description;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/test/resource")
@Description("This is a mock resource")
public class MockResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Description("get test")
  @Path("api")
  public Response getTest() {
    return Response.ok().build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Description("get another test")
  @Path("api2")
  public Response getAnotherTest(@Description("parameter test") List<String> strings) {
    return Response.ok().build();
  }
}
