package com.reqres.api.endpoints;

public final class Endpoints {
    private Endpoints() {}

    public static final String BASE = "/api";
    public static final String USERS = BASE + "/users";
    public static final String SINGLE_USER = USERS + "/{id}";

    // New endpoints for additional ReqRes scenarios
    public static final String REGISTER = BASE + "/register";
    public static final String LOGIN = BASE + "/login";

    // Resources (called "unknown" on reqres.in)
    public static final String RESOURCES = BASE + "/unknown";
    public static final String SINGLE_RESOURCE = RESOURCES + "/{id}";
}