package com.reqres.api.services;

import com.reqres.api.config.RequestManager;
import com.reqres.api.endpoints.Endpoints;
import com.reqres.api.models.UserRequest;
import com.reqres.api.models.AuthRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {

    public Response createUser(UserRequest payload) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.USERS)
                .andReturn();
    }

    public Response getUser(String id) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .pathParam("id", id)
                .when()
                .get(Endpoints.SINGLE_USER)
                .andReturn();
    }

    public Response getUsers() {
        return given()
                .spec(RequestManager.getRequestSpec())
                .when()
                .get(Endpoints.USERS)
                .andReturn();
    }

    public Response getUsers(Integer page, Integer perPage) {
        io.restassured.specification.RequestSpecification req = RequestManager.getRequestSpec();
        if (page != null) {
            req = req.queryParam("page", page);
        }
        if (perPage != null) {
            req = req.queryParam("per_page", perPage);
        }
        return given()
                .spec(req)
                .when()
                .get(Endpoints.USERS)
                .andReturn();
    }

    public Response updateUser(String id, UserRequest payload) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .pathParam("id", id)
                .body(payload)
                .when()
                .patch(Endpoints.SINGLE_USER)
                .andReturn();
    }

    public Response deleteUser(String id) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .pathParam("id", id)
                .when()
                .delete(Endpoints.SINGLE_USER)
                .andReturn();
    }

    // New service methods
    public Response getResources() {
        return given()
                .spec(RequestManager.getRequestSpec())
                .when()
                .get(Endpoints.RESOURCES)
                .andReturn();
    }

    public Response getResource(String id) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .pathParam("id", id)
                .when()
                .get(Endpoints.SINGLE_RESOURCE)
                .andReturn();
    }

    public Response register(AuthRequest payload) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.REGISTER)
                .andReturn();
    }

    public Response login(AuthRequest payload) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.LOGIN)
                .andReturn();
    }

    public Response getUsersWithDelay(int seconds) {
        return given()
                .spec(RequestManager.getRequestSpec())
                .queryParam("delay", seconds)
                .when()
                .get(Endpoints.USERS)
                .andReturn();
    }
}