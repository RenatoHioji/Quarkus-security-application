package org.acme.token;

import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@RegisterRestClient
@Path("/security")
public interface RestClientWithTokenHeaderParam {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("userName")
    Uni<String> getUserName(@HeaderParam("Authorization") String authorization);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("adminName")
    Uni<String> getAdminName(@HeaderParam("Authorization") String authorization);
}
