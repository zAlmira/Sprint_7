import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.services.qa.scooter.api.client.DeleteOrderHttpClient;
import ru.praktikum.services.qa.scooter.api.client.MakeOrderHttpClient;
import ru.praktikum.services.qa.scooter.api.model.MakeOrder;
import ru.praktikum.services.qa.scooter.api.model.MakeOrderResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class MakeOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final int expectedCode;
    private final String colorGrey;
    private final String colorBlack;
    private List<String> color = new ArrayList<String>();
    private Response makeOrderHttpClientResponse;


    public MakeOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String colorGrey, String colorBlack, int expectedCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colorGrey = colorGrey;
        this.colorBlack = colorBlack;
        this.expectedCode = expectedCode;
    }

    @Parameterized.Parameters(name = "Проверка указания цвета. Тестовые данные: {8} {9}")
    public static Object[][] dataForMakeOrder() {
        return new Object[][]{
                {"kallen", "kallenovich", "Odesskay,22", "4", "98768767876", 3, "12.08.2023", "mycomment", "GREY", "", 201},
                {"kallen", "kallenovich", "Odesskay,22", "4", "98768767876", 3, "12.08.2023", "mycomment", "GREY", "BLACK", 201},
                {"kallen", "kallenovich", "Odesskay,22", "4", "98768767876", 3, "12.08.2023", "mycomment", "", "", 201},
        };
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    public void makeOrderSuccess() {
        color.add(colorGrey);
        color.add(colorBlack);
        MakeOrder makeOrder = new MakeOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        MakeOrderHttpClient makeOrderHttpClient = new MakeOrderHttpClient();
        makeOrderHttpClientResponse = makeOrderHttpClient.doPostMakeOrder(makeOrder, color);
        makeOrderHttpClientResponse.then().statusCode(expectedCode).and().assertThat().body("track", notNullValue());
    }

    @After
    public void deleteTestData() {
        color.clear();
        MakeOrderResponse makeOrderResponse = makeOrderHttpClientResponse.body()
                .as(MakeOrderResponse.class);
        DeleteOrderHttpClient deleteOrderHttpClient = new DeleteOrderHttpClient();
        Response responseDeleteOrder = deleteOrderHttpClient.doDeleteOrder(makeOrderResponse);
    }
}
