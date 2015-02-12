package io.takari.swagger.v12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation of the ApiDeclaration interface
 *
 * @see ApiDeclaration
 */

public class ApiDeclaration {
  public final static String DEFAULT_API_VERSION = "1.0.0";
  private String apiVersion;
  private String swaggerVersion = "1.1";
  private String basePath;
  private String resourcePath;
  private final List<Api> apis = new ArrayList<Api>();
  private final Set<String> produces = new HashSet<String>();
  private final Set<String> consumes = new HashSet<String>();
  private final Map<String, Model> models = new HashMap<String, Model>();

  /*
  public ApiDeclaration(String basePath, String resourcePath) {
    this.basePath = basePath;
    this.resourcePath = resourcePath;
  }
  */

  public String getSwaggerVersion() {
    return swaggerVersion;
  }

  public void setSwaggerVersion(String swaggerVersion) {
    assert swaggerVersion != null : "swaggerVersion can not be null";

    this.swaggerVersion = swaggerVersion;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    assert apiVersion != null : "apiVersion can not be null";

    this.apiVersion = apiVersion;
  }

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    assert basePath != null : "basePath can not be null";

    this.basePath = basePath;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public List<Api> getApis() {
    return Collections.unmodifiableList(apis);
  }

  public void removeApi(Api api) {
    assert api != null : "api can not be null";

    synchronized (apis) {
      apis.remove(api);
    }
  }

  public void addApi(Api api) {
    apis.add(api);
  }

  public Api addApi(String path) {
    assert path != null : "Can not add api with null path";
    assert getApi(path) == null : "Api already exists at path [" + path + "]";
    synchronized (apis) {
      Api api = new Api(path);
      apis.add(api);
      return api;
    }
  }

  public Api getApi(String path) {
    assert path != null : "api path can not be null";

    synchronized (apis) {
      for (Api api : apis) {
        if (api.getPath().equals(path)) {
          return api;
        }
      }

      return null;
    }
  }

  public Collection<String> getProduces() {
    return Collections.unmodifiableCollection(produces);
  }

  public void removeProduces(String produces) {
    this.produces.remove(produces);
  }

  public void addProduces(String produces) {
    assert produces != null : "produces can not be null";

    this.produces.add(produces);
  }

  public Collection<String> getConsumes() {
    return Collections.unmodifiableCollection(consumes);
  }

  public void removeConsumes(String consumes) {
    this.produces.remove(consumes);
  }

  public void addConsumes(String consumes) {
    assert consumes != null : "consumes can not be null";
    this.consumes.add(consumes);
  }

  public Collection<Model> getModels() {
    return models.values();
  }

  public Model getModel(String id) {
    return models.get(id);
  }

  public void addModel(Model model) {
    models.put(model.getId(), model);
  }
}
