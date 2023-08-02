package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteCourierHttpClient extends BaseHttpClient {
    private final String URL;

    public DeleteCourierHttpClient() {
        super();
        URL = BaseHttpClient.BASE_URI + "api/v1/courier/";
    }

    @Step("Delete courier")
    public Response doDeleteCourier(String id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(URL + id);
    }
}
