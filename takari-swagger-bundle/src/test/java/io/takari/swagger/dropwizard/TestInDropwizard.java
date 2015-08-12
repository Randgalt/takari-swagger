package io.takari.swagger.dropwizard;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import io.takari.swagger.dropwizard.mocks.MockApplication;
import org.eclipse.jetty.util.thread.ShutdownThread;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestInDropwizard {
  @BeforeClass
  public static void setup() throws Exception {
    new MockApplication().run("server");
  }

  @AfterClass
  public static void tearDown() {
    ShutdownThread.getInstance().run();
  }

  @Test
  public void testSwaggerUi() throws Exception {
    // make sure we can get parts of the Swagger UI

    URL urlFromClasspath = Resources.getResource("swagger-ui/index.html");
    String classpathStr = Resources.toString(urlFromClasspath, Charsets.UTF_8);

    URL urlForRequest = new URL("http://localhost:8080/swagger-ui/index.html");
    try (InputStream requestStream = urlForRequest.openStream()) {
      byte[] requestBytes = ByteStreams.toByteArray(requestStream);
      assertEquals(new String(requestBytes, Charsets.UTF_8), classpathStr);
    }
  }

  @Test
  public void testSchema() throws Exception {
    // make sure we can get parts of the Swagger UI

    String expectedSchema = Resources.toString(Resources.getResource("schema.json"), Charsets.UTF_8);

    URL url = new URL("http://localhost:8080/docs/");
    try (InputStream requestStream = url.openStream()) {
      String schema = new String(ByteStreams.toByteArray(requestStream));
      assertEquals(fix(expectedSchema), fix(schema));
    }
  }

  private static String fix(String str) {
    return str.replaceAll("\\s", "");
  }
}
