package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.qa.scooter.api.model.MakeOrder;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MakeOrderHttpClient extends BaseHttpClient {
    private final String URL;

    public MakeOrderHttpClient() {
        super();
        URL = BaseHttpClient.BASE_URI + "api/v1/orders";
    }

    @Step("Make order")
    public Response doPostMakeOrder(MakeOrder makeOrder, List<String> color) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrder)
                .and()
                .body(color)
                .when()
                .post(URL);
    }
}
