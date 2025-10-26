package com.reqres.api.models;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String job;

    public UserRequest() {}

    public UserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }
}