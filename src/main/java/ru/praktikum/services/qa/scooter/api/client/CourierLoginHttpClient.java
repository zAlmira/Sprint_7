package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.qa.scooter.api.model.CourierLogin;
import ru.praktikum.services.qa.scooter.api.model.CourierLoginWithoutLogin;

import static io.restassured.RestAssured.given;

public class CourierLoginHttpClient extends BaseHttpClient {
    private final String URL;

    public CourierLoginHttpClient() {
        super();
        URL = BASE_URI + "api/v1/courier/login";
    }

    @Step("Get response for login with login and password")
    public Response getLoginResponse(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post(URL);
    }

    @Step("Get response for login without login")
    public Response getLoginResponseWithoutLogin(CourierLoginWithoutLogin courierLoginWithoutLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLoginWithoutLogin)
                .when()
                .post(URL);
    }

}
