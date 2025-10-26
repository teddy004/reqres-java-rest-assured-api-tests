package com.reqres.api.models;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String job;
    private String createdAt;
    private String updatedAt;

    public UserResponse() {}
}