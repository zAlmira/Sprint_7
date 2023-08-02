import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.services.qa.scooter.api.client.CourierLoginHttpClient;
import ru.praktikum.services.qa.scooter.api.client.DeleteCourierHttpClient;
import ru.praktikum.services.qa.scooter.api.client.MakeCourierHttpClient;
import ru.praktikum.services.qa.scooter.api.model.*;

import static org.hamcrest.core.IsEqual.equalTo;

public class MakeCourierTest {

    @Test
    @DisplayName("Check status code of /api/v1/courier")
    public void makeCourierSuccess() {
        MakeCourierHttpClient makeCourierHttpClient = new MakeCourierHttpClient();
        Response getMakeCourierResponse = makeCourierHttpClient.getMakeCourierResponse(new MakeCourier("cloud", "cloudpassword", "cloud"));
        MakeCourierResponse makeCourierResponse =
                getMakeCourierResponse.body().as(MakeCourierResponse.class);
        getMakeCourierResponse.then().statusCode(201).and().assertThat().body("ok", equalTo(makeCourierResponse.getOk()));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier without login")
    public void makeCourierWithoutLogin() {
        MakeCourierHttpClient makeCourierHttpClient = new MakeCourierHttpClient();
        Response getMakeCourierResponseWithoutLogin = makeCourierHttpClient.getMakeCourierResponseWithoutLogin(new MakeCourierWithoutLogin("cloudpassword", "cloud"));
        getMakeCourierResponseWithoutLogin.then().statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier with existable login")
    public void makeCourierWithExistableLogin() {
        MakeCourierHttpClient makeCourierHttpClient = new MakeCourierHttpClient();
        Response getMakeCourierResponse = makeCourierHttpClient.getMakeCourierResponse(new MakeCourier("cloud", "cloudpassword", "cloud"));
        getMakeCourierResponse = makeCourierHttpClient.getMakeCourierResponse(new MakeCourier("cloud", "cloudpassword", "cloud"));
        getMakeCourierResponse.then().statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется"));
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


