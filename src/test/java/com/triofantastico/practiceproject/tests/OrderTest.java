package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.httpclient.restful.OrderClient;
import com.triofantastico.practiceproject.model.order.Order;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void store_valid_petstore_order() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        String shipJsonDate = createdOrder.getShipDate();

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(createdOrder.getPetId(), desiredOrder.getPetId());
        Assertions.assertEquals(createdOrder.getQuantity(), desiredOrder.getQuantity());
        Assertions.assertEquals(shipJsonDate.replaceFirst(".{5}$", "Z"), desiredOrder.getShipDate());
        Assertions.assertEquals(createdOrder.getStatus(), desiredOrder.getStatus());
        Assertions.assertEquals(createdOrder.getComplete(), desiredOrder.getComplete());
    }
}
