import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetListOfOrdersTest {
    Response responseOfMakeOrder;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String colorGrey;
    private String colorBlack;
    private List<String> color = new ArrayList<String>();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        color.add(colorGrey);
        color.add(colorBlack);
        MakeOrder makeOrder = new MakeOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        responseOfMakeOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrder)
                .and()
                .body(color)
                .post("/api/v1/orders");
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    public void getListOfOrdersSuccess() {
        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }

    @After
    public void deleteTestData() {
        color.clear();
        MakeOrderResponse makeOrderResponse = responseOfMakeOrder.body()
                .as(MakeOrderResponse.class);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(makeOrderResponse)
                .put("/api/v1/orders/cancel");
    }

}

