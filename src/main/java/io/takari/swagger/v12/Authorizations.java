package io.takari.swagger.v12;

import java.util.List;

public interface Authorizations {
  public enum AuthorizationType {
    OAUTH2, API_KEY, BASIC
  }

  public List<Authorization> getAuthorizations();

  public List<Authorization> getAuthorizationsByType(AuthorizationType type);

  public Authorization addAuthorization(String name, AuthorizationType type);

  public void removeAuthorization(Authorization authorization);

  public interface Authorization {
    AuthorizationType getType();

    String getName();
  }

  public interface ApiKeyAuthorization extends Authorization {
    public String getKeyName();

    public String getPassAs();

    public void setKeyName(String keyName);

    public void setPassAs(String passAs);
  }

  public interface BasicAuthorization extends Authorization {

  }

  public interface OAuth2Authorization extends Authorization {
    public Scope addScope(String name, String description);

    public Scope[] getScopes();

    public void removeScope(Scope scope);

    public ImplicitGrant getImplicitGrant();

    public AuthorizationCodeGrant getAuthorizationCodeGrant();

    public interface ImplicitGrant {
      String getLoginEndpointUrl();

      void setLoginEndpoint(String loginEndpoint);

      String getTokenName();

      void setTokenName(String tokenName);
    }

    public interface AuthorizationCodeGrant {
      String getTokenRequestEndpointUrl();

      void setTokenRequestEndpoint(String tokenRequestEndpoint);

      String getClientIdName();

      void setClientIdName(String clientIdName);

      String getClientSecretName();

      void setClientSecretName(String clientSecretName);

      String getTokenEndpointUrl();

      void setTokenEndpoint(String tokenEndpoint);

      String getTokenName();

      void setTokenName(String tokenName);
    }

    public interface Scope {
      String getName();

      void setName(String name);

      String getDescription();

      void setDescription(String description);

    }
  }
}
