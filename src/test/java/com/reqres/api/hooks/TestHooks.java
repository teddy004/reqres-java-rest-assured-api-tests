package com.reqres.api.hooks;

import io.cucumber.java.Before;

public class TestHooks {

    @Before
    public void beforeScenario() {
        // If using local WireMock stubs, no-op (retained for stubbed runs)
        String useWireMock = System.getProperty("useWireMock");
        if (useWireMock != null && useWireMock.equalsIgnoreCase("true")) {
            return;
        }

        // No API key required for public ReqRes API; run tests by default.
    }
}