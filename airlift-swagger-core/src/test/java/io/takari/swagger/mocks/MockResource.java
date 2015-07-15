package io.takari.swagger.mocks;

import io.takari.swagger.annotations.Description;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/test")
@Description("This is a mock resource")
public class MockResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Description("get test")
  public Response getTest(@Description("parameter test") List<String> strings) {
    return Response.ok().build();
  }
}
