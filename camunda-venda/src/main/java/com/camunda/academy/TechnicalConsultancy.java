package com.camunda.academy;

import java.time.Duration;

import com.camunda.academy.handler.MachineExistenceHandler;
import com.camunda.academy.handler.OrderMachineHandler;
import com.camunda.academy.handler.ReserveMachineHandler;
import com.camunda.academy.handler.SaveDatabaseHandler;
import com.camunda.academy.handler.TechnicalConsultancyHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class TechnicalConsultancy {

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
                    .jobType("techinicalCunsultancy")
                    .handler(new TechnicalConsultancyHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("searchDB")
                    .handler(new MachineExistenceHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("reserveMachine")
                    .handler(new ReserveMachineHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("orderMachine")
                    .handler(new OrderMachineHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            client.newWorker()
                    .jobType("saveSale")
                    .handler(new SaveDatabaseHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            System.out.println("Camunda sales workers started. Press Ctrl+C to stop.");
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
