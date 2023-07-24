import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.MakeOrder;
import ru.praktikum_services.qa_scooter.MakeOrderResponse;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
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
    private Response response;


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

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    public void makeOrderSuccess() {
        color.add(colorGrey);
        color.add(colorBlack);
        MakeOrder makeOrder = new MakeOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrder)
                .and()
                .body(color)
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and().statusCode(expectedCode);
    }

    @After
    public void deleteTestData() {
        color.clear();
        MakeOrderResponse makeOrderResponse = response.body()
                .as(MakeOrderResponse.class);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrderResponse)
                .put("/api/v1/orders/cancel");
    }
}
