package org.acme.oidc;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import io.smallrye.mutiny.Uni;
@RegisterRestClient
@OidcClientFilter
@Path("/security")
public interface RestClientWithOidcClientFilter {
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("userName")
        Uni<String> getUserName();

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("adminName")
        Uni<String> getAdminName();
}
