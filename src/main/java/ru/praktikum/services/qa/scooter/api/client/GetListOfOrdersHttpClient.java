package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetListOfOrdersHttpClient extends BaseHttpClient {
    private final String URL;

    public GetListOfOrdersHttpClient() {
        super();
        URL = BaseHttpClient.BASE_URI + "api/v1/orders";
    }

    @Step("Get list of orders")
    public Response doGetListOfOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(URL);
    }
}
