package ru.praktikum.services.qa.scooter.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.qa.scooter.api.model.MakeCourier;
import ru.praktikum.services.qa.scooter.api.model.MakeCourierWithoutLogin;

import static io.restassured.RestAssured.given;

public class MakeCourierHttpClient extends BaseHttpClient {
    private final String URL;

    public MakeCourierHttpClient() {
        super();
        URL = BASE_URI + "api/v1/courier";
    }

    @Step("Get response for make courier with login, password, first name")
    public Response getMakeCourierResponse(MakeCourier makeCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(makeCourier)
                .when()
                .post(URL);
    }

    @Step("Get response for make courier without login")
    public Response getMakeCourierResponseWithoutLogin(MakeCourierWithoutLogin makeCourierWithoutLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(makeCourierWithoutLogin)
                .when()
                .post(URL);
    }
}
