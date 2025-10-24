package com.reqres.api.hooks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockHooks {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void startWireMock() {
        // default to true so tests run reliably in local CI/dev without needing extra vars
        String useWireMock = System.getProperty("useWireMock");
        if (useWireMock == null || useWireMock.isEmpty()) {
            useWireMock = System.getenv("USE_WIREMOCK");
        }
        if (useWireMock == null || useWireMock.isEmpty()) {
            useWireMock = "true"; // default to enabling WireMock for test runs
            System.setProperty("useWireMock", "true");
        }
        if (!useWireMock.equalsIgnoreCase("true")) {
            return;
        }

        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
        wireMockServer.start();

        configureFor("localhost", 8089);

        // Default GET /api/users -> 200 with default page/per_page
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":1,\"per_page\":6,\"data\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4},{\"id\":5},{\"id\":6}]}")));

        // GET /api/users?page=1&per_page=3
        stubFor(get(urlEqualTo("/api/users?page=1&per_page=3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":1,\"per_page\":3,\"data\":[{\"id\":1},{\"id\":2},{\"id\":3}] }")));

        // GET /api/users?page=2&per_page=2
        stubFor(get(urlEqualTo("/api/users?page=2&per_page=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":2,\"per_page\":2,\"data\":[{\"id\":3},{\"id\":4}] }")));

        // GET /api/users?page=1&per_page=6 (explicit stub for the new example)
        stubFor(get(urlEqualTo("/api/users?page=1&per_page=6"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":1,\"per_page\":6,\"data\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4},{\"id\":5},{\"id\":6}]}")));

        // GET /api/users/2 -> 200
        stubFor(get(urlEqualTo("/api/users/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"}}")));

        // GET /api/users/23 -> 404 (not found)
        stubFor(get(urlEqualTo("/api/users/23"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // POST /api/users -> 201 (create)
        stubFor(post(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"123\",\"name\":\"morpheus\",\"job\":\"leader\",\"createdAt\":\"2025-10-24T00:00:00Z\"}")));

        // PATCH /api/users/2 -> 200 (update user 2, return job expected by scenario)
        stubFor(patch(urlEqualTo("/api/users/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"name\":\"morpheus\",\"job\":\"zion resident\",\"updatedAt\":\"2025-10-24T00:00:00Z\"}")));

        // PATCH /api/users/123 -> 200 (update created user)
        stubFor(patch(urlEqualTo("/api/users/123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"name\":\"neo\",\"job\":\"the one\",\"updatedAt\":\"2025-10-24T00:00:00Z\"}")));

        // DELETE /api/users/2 -> 204
        stubFor(delete(urlEqualTo("/api/users/2"))
                .willReturn(aResponse()
                        .withStatus(204)));

        // DELETE /api/users/123 -> 204 (delete created user)
        stubFor(delete(urlEqualTo("/api/users/123"))
                .willReturn(aResponse()
                        .withStatus(204)));

        // Resources: GET /api/unknown -> 200 with data
        stubFor(get(urlEqualTo("/api/unknown"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":1,\"per_page\":6,\"data\":[{\"id\":1},{\"id\":2}]}")
                ));

        // GET /api/unknown/2 -> 200
        stubFor(get(urlEqualTo("/api/unknown/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\":{\"id\":2,\"name\":\"fuchsia\"}}")));

        // GET /api/unknown/23 -> 404
        stubFor(get(urlEqualTo("/api/unknown/23"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // Delayed users: match query param delay=3 -> 200
        stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("delay", equalTo("3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"page\":1,\"per_page\":6,\"data\":[{\"id\":1},{\"id\":2},{\"id\":3}]}")));

        // AUTH stubs: place missing-password (error) cases before success so requests without password match the error stub
        // Register: missing password -> 400
        stubFor(post(urlEqualTo("/api/register"))
                .withRequestBody(matchingJsonPath("$.email"))
                .withRequestBody(notMatching("(?s).*\\\"password\\\".*"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"Missing password\"}")));

        // Register: success when both email and password present
        stubFor(post(urlEqualTo("/api/register"))
                .withRequestBody(matchingJsonPath("$.email"))
                .withRequestBody(matchingJsonPath("$.password"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":4,\"token\":\"QpwL5tke4Pnpja7X4\"}")));

        // Login: missing password -> 400
        stubFor(post(urlEqualTo("/api/login"))
                .withRequestBody(matchingJsonPath("$.email"))
                .withRequestBody(notMatching("(?s).*\\\"password\\\".*"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"Missing password\"}")));

        // Login: success when both email and password present
        stubFor(post(urlEqualTo("/api/login"))
                .withRequestBody(matchingJsonPath("$.email"))
                .withRequestBody(matchingJsonPath("$.password"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\":\"QpwL5tke4Pnpja7X4\"}")));

        // Set base URI system property so RequestManager picks up the WireMock server
        System.setProperty("reqres.baseUri", "http://localhost:8089");
        System.out.println("WireMock started on http://localhost:8089 and stubs registered.");
    }

    @AfterAll
    public static void stopWireMock() {
        String useWireMock = System.getProperty("useWireMock");
        if (useWireMock == null || !useWireMock.equalsIgnoreCase("true")) {
            return;
        }
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("WireMock stopped.");
        }
    }
}