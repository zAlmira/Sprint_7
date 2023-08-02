package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.qa.scooter.api.model.MakeOrderResponse;

import static io.restassured.RestAssured.given;

public class DeleteOrderHttpClient extends BaseHttpClient {
    private final String URL;

    public DeleteOrderHttpClient() {
        super();
        URL = BaseHttpClient.BASE_URI + "api/v1/orders/cancel";
    }

    @Step("Delete order")
    public Response doDeleteOrder(MakeOrderResponse makeOrderResponse) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrderResponse)
                .when()
                .put(URL);
    }
}
