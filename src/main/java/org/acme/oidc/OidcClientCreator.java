package org.acme.oidc;

import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClientConfig;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import io.quarkus.oidc.client.OidcClientConfig.Grant.Type;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;

@ApplicationScoped
public class OidcClientCreator {

    @Inject
    OidcClients client;

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String oidcProviderAddress;

    private volatile OidcClient oidcClient;

    public void startup(@Observes StartupEvent startupEvent){
        createOidcClient().subscribe().with(client -> {oidcClient = client;});
    }

    public OidcClient getOidcClient() {
        return oidcClient;
    }


    private Uni<OidcClient> createOidcClient() {
        OidcClientConfig cfg = new OidcClientConfig();
        cfg.setId("myclient");
        cfg.setAuthServerUrl(oidcProviderAddress);
        cfg.setClientId("backend-service");
        cfg.getCredentials().setSecret("secret");
        cfg.getGrant().setType(Type.PASSWORD);
        cfg.setGrantOptions(Map.of("password",
                Map.of("username", "alice", "password", "alice")));
        return client.newClient(cfg);
    }
}
