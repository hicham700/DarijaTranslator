package com.example.translator;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final String USERNAME = "admin"; // Nom d'utilisateur
    private static final String PASSWORD = "12345"; // Mot de passe

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String encodedCredentials = authHeader.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(encodedCredentials));
        StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
        String username = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String password = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header("WWW-Authenticate", "Basic realm=\"TranslatorService\"")
                        .entity("Unauthorized access")
                        .build()
        );
    }
}
