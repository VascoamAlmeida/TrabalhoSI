package com.camunda.academy;

import com.camunda.academy.handler.ArchiveHandler;
import com.camunda.academy.handler.CancelHandler;
import com.camunda.academy.handler.NotificationHandler;
import com.camunda.academy.handler.ReceiveHandler;
import com.camunda.academy.handler.ServiceHandler;
import com.camunda.academy.handler.StartHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.time.Duration;

public class machineHub {

    private static final String ZEEBE_AUTHORIZATION_SERVER_URL =
            "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";

    public static void main(String[] args) {
        final String zeebeAddress = requireEnvironmentVariable("CAMUNDA_ZEEBE_ADDRESS");
        final String zeebeClientId = requireEnvironmentVariable("CAMUNDA_ZEEBE_CLIENT_ID");
        final String zeebeClientSecret = requireEnvironmentVariable("CAMUNDA_ZEEBE_CLIENT_SECRET");

        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(zeebeClientId)
                        .clientSecret(zeebeClientSecret)
                        .build();

        try (final ZeebeClient client =
                     ZeebeClient.newClientBuilder()
                             .gatewayAddress(zeebeAddress)
                             .credentialsProvider(credentialsProvider)
                             .build()) {

            client.newWorker()
                    .jobType("marcarReparacao")
                    .handler(new ServiceHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("receiveNotification")
                    .handler(new NotificationHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("cancelaReparacao")
                    .handler(new CancelHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("iniciarReparacao")
                    .handler(new StartHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("receiveReport")
                    .handler(new ReceiveHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("saveRepair")
                    .handler(new ArchiveHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            System.out.println("Camunda repair workers started. Press Ctrl+C to stop.");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String requireEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable: " + name
            );
        }
        return value;
    }
}
