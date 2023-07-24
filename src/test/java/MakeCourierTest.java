import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class MakeCourierTest {
    private MakeCourier makeCourier;
    private MakeCourierWithoutLogin makeCourierWithoutLogin;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier")
    public void makeCourierSuccess() {
        makeCourier = new MakeCourier("kallen", "kallenpassword", "kallen");
        Response response = given()
                .header("Content-type", "application/json")
                .body(makeCourier)
                .post("/api/v1/courier");
        MakeCourierResponse makeCourierResponse =
                response.body().as(MakeCourierResponse.class);
        response.then().assertThat().body("ok", equalTo(makeCourierResponse.getOk()))
                .and().statusCode(201);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier without login")
    public void makeCourierWithoutLogin() {
        makeCourierWithoutLogin = new MakeCourierWithoutLogin("kallenpassword", "kallen");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(makeCourierWithoutLogin)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier with existable login")
    public void makeCourierWithExistableLogin() {
        makeCourier = new MakeCourier("kallen", "kallenpassword", "kallen");
        given()
                .header("Content-type", "application/json")
                .body(makeCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(makeCourier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and().statusCode(409);
    }

    @After
    public void deleteTestData() {
        CourierLogin courierLogin = new CourierLogin("kallen", "kallenpassword");
        CourierId courierId = given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post("/api/v1/courier/login")
                .body()
                .as(CourierId.class);
        given()
                .header("Content-type", "application/json")
                .delete("/api/v1/courier/" + courierId.getId());
    }

}


