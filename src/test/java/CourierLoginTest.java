import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.services.qa.scooter.api.client.CourierLoginHttpClient;
import ru.praktikum.services.qa.scooter.api.client.DeleteCourierHttpClient;
import ru.praktikum.services.qa.scooter.api.client.MakeCourierHttpClient;
import ru.praktikum.services.qa.scooter.api.model.CourierId;
import ru.praktikum.services.qa.scooter.api.model.CourierLogin;
import ru.praktikum.services.qa.scooter.api.model.CourierLoginWithoutLogin;
import ru.praktikum.services.qa.scooter.api.model.MakeCourier;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class CourierLoginTest {
    @Before
    public void setUp() {
        MakeCourierHttpClient makeCourierHttpClient = new MakeCourierHttpClient();
        makeCourierHttpClient.getMakeCourierResponse(new MakeCourier("cloud", "cloudpassword", "cloud"));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login without login")
    public void doPostCourierLoginWithoutLoginCode400() {
        CourierLoginHttpClient courierLoginHttpClient = new CourierLoginHttpClient();
        Response getLoginResponseWithoutLogin = courierLoginHttpClient.getLoginResponseWithoutLogin(new CourierLoginWithoutLogin("cloudpassword"));
        getLoginResponseWithoutLogin.then().statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login with incorrect pair of login and password")
    public void doPostCourierLoginWithIncorrectPairOfLoginAndPassword() {
        CourierLoginHttpClient courierLoginHttpClient = new CourierLoginHttpClient();
        Response loginResponse = courierLoginHttpClient.getLoginResponse(new CourierLogin("cloud", "cloudpassword2"));
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login with correct login and password")
    public void doPostCourierLoginSuccess() {
        CourierLoginHttpClient courierLoginHttpClient = new CourierLoginHttpClient();
        Response loginResponse = courierLoginHttpClient.getLoginResponse(new CourierLogin("cloud", "cloudpassword"));
        loginResponse.then().statusCode(200).and().assertThat().body("id", notNullValue());
    }

    @After
    public void deleteTestData() {
        CourierLoginHttpClient courierLoginHttpClient = new CourierLoginHttpClient();
        Response loginResponse = courierLoginHttpClient.getLoginResponse(new CourierLogin("cloud", "cloudpassword"));
        CourierId courierId = loginResponse.body().as(CourierId.class);
        DeleteCourierHttpClient deleteCourierHttpClient = new DeleteCourierHttpClient();
        Response deleteCourier = deleteCourierHttpClient.doDeleteCourier(courierId.getId());
    }
}
