package org.acme.resources;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/security/protected")
@Authenticated
public class ProtectedResource {
    @Inject
    JsonWebToken token;

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("userName")
    public Uni<String> userName(){
        return Uni.createFrom().item(token.getName());
    }


    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("adminName")
    public Uni<String> adminName(){
        return Uni.createFrom().item(token.getName());
    }
}
