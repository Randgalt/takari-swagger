package io.takari.swagger.dropwizard.mocks;

import com.google.common.collect.Sets;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.takari.swagger.ApiDocsResource;
import io.takari.swagger.Swagger;
import io.takari.swagger.SwaggerBuilder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class MockApplication extends Application<Configuration> {
  @Override
  public void initialize(Bootstrap<Configuration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/swagger-ui", "/swagger-ui", "index.html"));
  }

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {
    environment.jersey().register(ApiDocsResource.class);
    environment.jersey().register(MockResource.class);

    final AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        Swagger swagger = new SwaggerBuilder().basePath("http://localhost:8080/").jaxRsClasses(Sets.<Class<?>>newHashSet(MockResource.class)).build();
        bind(swagger).to(Swagger.class);
      }
    };
    environment.jersey().register(binder);

    FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    filter.setInitParameter("allowCredentials", "true");
  }
}
