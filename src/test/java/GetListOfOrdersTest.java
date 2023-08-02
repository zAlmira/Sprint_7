import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.services.qa.scooter.api.client.DeleteOrderHttpClient;
import ru.praktikum.services.qa.scooter.api.client.GetListOfOrdersHttpClient;
import ru.praktikum.services.qa.scooter.api.client.MakeOrderHttpClient;
import ru.praktikum.services.qa.scooter.api.model.MakeOrder;
import ru.praktikum.services.qa.scooter.api.model.MakeOrderResponse;

import java.util.ArrayList;
import java.util.List;

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
        color.add(colorGrey);
        color.add(colorBlack);
        MakeOrder makeOrder = new MakeOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        MakeOrderHttpClient makeOrderHttpClient = new MakeOrderHttpClient();
        responseOfMakeOrder = makeOrderHttpClient.doPostMakeOrder(makeOrder, color);
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    public void getListOfOrdersSuccess() {
        GetListOfOrdersHttpClient getListOfOrdersHttpClient = new GetListOfOrdersHttpClient();
        Response getListOfOrdersResponse = getListOfOrdersHttpClient.doGetListOfOrders();
        getListOfOrdersResponse.then().statusCode(200).and().assertThat().body("orders", notNullValue());
    }

    @After
    public void deleteTestData() {
        color.clear();
        MakeOrderResponse makeOrderResponse = responseOfMakeOrder.body()
                .as(MakeOrderResponse.class);
        DeleteOrderHttpClient deleteOrderHttpClient = new DeleteOrderHttpClient();
        Response responseDeleteOrder = deleteOrderHttpClient.doDeleteOrder(makeOrderResponse);
    }

}

