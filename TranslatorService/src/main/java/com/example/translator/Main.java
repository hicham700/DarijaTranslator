package com.example.translator;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        ResourceConfig config = new ResourceConfig()
                .packages("com.example.translator"); // package de tes resources

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
        System.out.println("✅ Serveur démarré sur " + BASE_URI);
        System.out.println("👉 Appuyez sur ENTRÉE pour arrêter le serveur...");
        System.in.read();
        server.shutdownNow();
    }
}
