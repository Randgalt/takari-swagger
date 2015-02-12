package io.takari.swagger.v12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourceListing {

  private String apiVersion;
  private String swaggerVersion = "1.2";
  private String basePath;
  private AuthorizationsImpl authorizations;

  private final List<ResourceListingApi> apis = new ArrayList<ResourceListingApi>();
  private Info info;

  public ResourceListing(String swaggerVersion) {
    this.swaggerVersion = swaggerVersion;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getSwaggerVersion() {
    return swaggerVersion;
  }

  public void setSwaggerVersion(String swaggerVersion) {
    this.swaggerVersion = swaggerVersion;
  }

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public List<ResourceListingApi> getApis() {
    return Collections.unmodifiableList(apis);
  }

  public ResourceListingApi addApi(ApiDeclaration apiDeclaration, String path) {
    assert apiDeclaration != null : "apiDeclaration can not be null";
    assert getApi(apiDeclaration.getResourcePath()) == null : "Can not add API to Resource Listing; path already exists";
    synchronized (apis) {
      ResourceListingApi api = new ResourceListingApi(path, "description");
      apis.add(api);
      return api;
    }
  }

  public Info getInfo() {
    if (info == null) {
      info = new Info();
    }

    return info;
  }

  public Authorizations getAuthorizations() {
    if (authorizations == null) {
      authorizations = new AuthorizationsImpl();
    }

    return authorizations;
  }

  public ResourceListingApi getApi(String path) {
    synchronized (apis) {
      for (ResourceListingApi api : apis) {
        if (api.getPath().equals(path)) {
          return api;
        }
      }
      return null;
    }
  }

  /*
  public Collection<Model> getApisModels() {
    List<Model> models = new ArrayList<Model>();
    for (ResourceListingApi api : getApis()) {
      models.addAll(api.getDeclaration().getModels());
    }
    return models;
  }
  */

  public static class ResourceListingApi {
    
    private String path;
    private String description;

    public ResourceListingApi(String path, String description) {
      this.path = path;
      this.description = description;
    }


    public String getDescription() {
      return description;
    }


    public String getPath() {
      return path;
    }
  }
}
