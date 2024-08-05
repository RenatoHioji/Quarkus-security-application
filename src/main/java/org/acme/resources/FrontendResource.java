package org.acme.resources;

import io.quarkus.oidc.client.Tokens;
import io.quarkus.oidc.client.runtime.TokensHelper;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.oidc.OidcClientCreator;
import org.acme.oidc.RestClientWithOidcClientFilter;
import org.acme.token.RestClientWithTokenHeaderParam;
import org.acme.token.RestClientWithTokenPropagationFilter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/security/frontend")
public class FrontendResource {
    @Inject
    @RestClient
    RestClientWithOidcClientFilter restClientWithOidcClientFilter;

    @Inject
    @RestClient
    RestClientWithTokenPropagationFilter restClientWithTokenPropagationFilter;

    @Inject
    OidcClientCreator oidcClientCreator;
    TokensHelper tokenHelper = new TokensHelper();

    @Inject
    @RestClient
    RestClientWithTokenHeaderParam restClientWithTokenHeaderParam;

    @GET
    @Path("user-name-with-oidc-client-token")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getUserNameWithOidcClientToken(){
        return restClientWithOidcClientFilter.getUserName();
    }

    @GET
    @Path("admin-name-with-oidc-client-token")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getAdminNameWithOidcClientToken() {
        return restClientWithOidcClientFilter.getAdminName();
    }


    @GET
    @Path("user-name-with-propagated-token")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getUserNameWithPropagatedToken() {
        return restClientWithTokenPropagationFilter.getUserName();
    }

    @GET
    @Path("admin-name-with-propagated-token")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getAdminNameWithPropagatedToken() {
        return restClientWithTokenPropagationFilter.getAdminName();
    }

    @GET
    @Path("user-name-with-oidc-client-token-header-param")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getUserNameWithOidcClientTokenHeaderParam(){
        return tokenHelper.getTokens(oidcClientCreator.getOidcClient()).onItem()
                .transformToUni(tokens -> restClientWithTokenHeaderParam.getUserName("Bearer " + tokens.getAccessToken()));
    }

    @GET
    @Path("admin-name-with-oidc-client-token-header-param")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getAdminNameWithOidcClientTokenHeaderParam() {
        return tokenHelper.getTokens(oidcClientCreator.getOidcClient()).onItem()
                .transformToUni(tokens -> restClientWithTokenHeaderParam.getAdminName("Bearer " + tokens.getAccessToken()));
    }

    @GET
    @Path("user-name-with-oidc-client-token-header-param-blocking")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserNameWithOidcClientTokenHeaderParamBlocking() {
        Tokens tokens = tokenHelper.getTokens(oidcClientCreator.getOidcClient()).await().indefinitely();
        return restClientWithTokenHeaderParam.getUserName("Bearer " + tokens.getAccessToken()).await().indefinitely();
    }
    @GET
    @Path("admin-name-with-oidc-client-token-header-param-blocking")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAdminNameWithOidcClientTokenHeaderParamBlocking() {
        Tokens tokens = tokenHelper.getTokens(oidcClientCreator.getOidcClient()).await().indefinitely();
        return restClientWithTokenHeaderParam.getAdminName("Bearer " + tokens.getAccessToken()).await().indefinitely();
    }
}
