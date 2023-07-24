import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.CourierId;
import ru.praktikum_services.qa_scooter.CourierLogin;
import ru.praktikum_services.qa_scooter.CourierLoginWithoutLogin;
import ru.praktikum_services.qa_scooter.MakeCourier;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class CourierLoginTest {
    private CourierLogin courierLogin;
    private MakeCourier makeCourier;
    private CourierLoginWithoutLogin courierLoginWithoutLogin;


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        makeCourier = new MakeCourier("satle", "satlepassword", "satle");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(makeCourier)
                .when()
                .post("/api/v1/courier");
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login without login")
    public void doPostCourierLoginWithoutLoginCode400() {
        courierLoginWithoutLogin = new CourierLoginWithoutLogin("satlepassword");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLoginWithoutLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login with incorrect pair of login and password")
    public void doPostCourierLoginWithIncorrectPairOfLoginAndPassword() {
        courierLogin = new CourierLogin("satle", "satlepassword2");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courierLogin)
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login with correct login and password")
    public void doPostCourierLoginSuccess() {
        courierLogin = new CourierLogin("satle", "satlepassword");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courierLogin)
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @After
    public void deleteTestData() {
        CourierLogin courierLogin = new CourierLogin("satle", "satlepassword");
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
