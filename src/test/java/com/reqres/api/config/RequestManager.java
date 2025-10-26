package com.reqres.api.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestManager {

    private static final String DEFAULT_BASE_URI = "https://reqres.in";

    public static RequestSpecification getRequestSpec() {
        String baseUri = System.getProperty("reqres.baseUri");
        if (baseUri == null || baseUri.isEmpty()) {
            baseUri = System.getenv("REQRES_BASE_URI");
        }
        if (baseUri == null || baseUri.isEmpty()) {
            baseUri = DEFAULT_BASE_URI;
        }

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON);

        String apiKey = System.getProperty("reqres.api.key");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getenv("REQRES_API_KEY");
        }
        if (apiKey != null && !apiKey.isEmpty()) {
            builder.addHeader("x-api-key", apiKey);
        }

        return builder.build();
    }

    public static boolean isApiKeyConfigured() {
        String apiKey = System.getProperty("reqres.api.key");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getenv("REQRES_API_KEY");
        }
        return apiKey != null && !apiKey.isEmpty();
    }

    public static ResponseSpecification getOkResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}