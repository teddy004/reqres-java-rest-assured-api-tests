package com.reqres.api.hooks;

import io.cucumber.java.Before;

public class TestHooks {

    @Before
    public void beforeScenario() {
        String useWireMock = System.getProperty("useWireMock");
        if (useWireMock != null && useWireMock.equalsIgnoreCase("true")) {
            return;
        }
    }
}