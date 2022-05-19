package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.constant.StatusConstant;
import com.triofantastico.practiceproject.helper.RandomGenerator;
import com.triofantastico.practiceproject.httpclient.restful.OrderClient;
import com.triofantastico.practiceproject.model.order.Order;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

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

    @Test
    void get_store_inventory()  {
        // ARRANGE
        OrderClient orderClient = new OrderClient();

        // ACT
        Response response = orderClient.getInventory();

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    void delete_created_order() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(createdOrder.getId());

        Response deleteResponse = orderClient.delete(orderId);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, deleteResponse.getStatusCode());
        Assertions.assertEquals(StatusConstant.STATUS_CODE_OK, deleteResponse.jsonPath().get("code").toString());
    }

    @Test
    void delete_order_with_negative_id() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(-createdOrder.getId());

        Response deleteResponse = orderClient.delete(orderId);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, deleteResponse.getStatusCode());
        Assertions.assertEquals(StatusConstant.STATUS_CODE_MESSAGE, deleteResponse.jsonPath().get("message").toString());
    }

    @Test
    void find_valid_purchase_order_by_id() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(createdOrder.getId());

        Response fetchValidOrderId = orderClient.getOrderById(orderId);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, fetchValidOrderId.getStatusCode());
    }

    @Test
    void not_valid_purchase_order_by_id() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(createdOrder.getId()*-1);

        Response fetchNotValidOrderId = orderClient.getOrderById(orderId);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, fetchNotValidOrderId.getStatusCode());
        Assertions.assertEquals(StatusConstant.STATUS_CODE, fetchNotValidOrderId.jsonPath().get("code").toString());
        Assertions.assertEquals(StatusConstant.STATUS_CODE_TYPE, fetchNotValidOrderId.jsonPath().get("type").toString());
        Assertions.assertEquals(StatusConstant.STATUS_CODE_NOT_FOUND, fetchNotValidOrderId.jsonPath().get("message").toString());
    }

}
