package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.constant.StatusConstant;
import com.triofantastico.practiceproject.httpclient.restful.OrderClient;
import com.triofantastico.practiceproject.model.order.Order;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void store_valid_pet_store_order_should_create_that_order() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        // ACT
        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        String shipJsonDate = createdOrder.getShipDate();

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(createdOrder.getPetId(), desiredOrder.getPetId());
        assertEquals(createdOrder.getQuantity(), desiredOrder.getQuantity());
        assertEquals(shipJsonDate.replaceFirst(".{5}$", "Z"), desiredOrder.getShipDate());
        assertEquals(createdOrder.getStatus(), desiredOrder.getStatus());
        assertEquals(createdOrder.getComplete(), desiredOrder.getComplete());
    }

    @Test
    void get_store_inventory()  {
        // ARRANGE
        OrderClient orderClient = new OrderClient();

        // ACT
        Response response = orderClient.getInventory();

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    void delete_created_order_should_return_specified_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(createdOrder.getId());

        // ACT
        Response deleteResponse = orderClient.delete(orderId);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, deleteResponse.getStatusCode());
        Assertions.assertAll("Delete existing path should successfully retrieve this response",
                () -> assertEquals(StatusConstant.STATUS_CODE_OK, deleteResponse.jsonPath().get("code").toString()),
                () -> assertEquals(StatusConstant.MESSAGE_TYPE, deleteResponse.jsonPath().get("type").toString()),
                () -> assertEquals(createdOrder.getId().toString(), deleteResponse.jsonPath().get("message").toString())
                            );
    }

    @Test
    void attempting_to_delete_order_with_negative_id_should_return_specified_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(-createdOrder.getId());

        // ACT
        Response deleteResponse = orderClient.delete(orderId);

        // ASSERT
        assertEquals(HttpStatus.SC_NOT_FOUND, deleteResponse.getStatusCode());
        assertEquals(StatusConstant.STATUS_CODE_NOT_FOUND, deleteResponse.jsonPath().get("code").toString());
        assertEquals(StatusConstant.MESSAGE_TYPE, deleteResponse.jsonPath().get("type").toString());
        assertEquals(StatusConstant.STATUS_CODE_MESSAGE, deleteResponse.jsonPath().get("message").toString());
    }

    @Test
    void fetching_valid_purchase_order_by_id_should_retrieve_specified_order() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        String shipJsonDate = createdOrder.getShipDate();
        Integer orderId = Math.toIntExact(createdOrder.getId());

        // ACT
        Response fetchValidOrderId = orderClient.getOrderById(orderId);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, fetchValidOrderId.getStatusCode());
        assertEquals(createdOrder.getPetId(), desiredOrder.getPetId());
        assertEquals(createdOrder.getQuantity(), desiredOrder.getQuantity());
        assertEquals(shipJsonDate.replaceFirst(".{5}$", "Z"), desiredOrder.getShipDate());
        assertEquals(createdOrder.getStatus(), desiredOrder.getStatus());
        assertEquals(createdOrder.getComplete(), desiredOrder.getComplete());
    }

    @Test
    void attempting_to_get_not_valid_purchase_order_by_id_should_return_spec_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.post(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Integer orderId = Math.toIntExact(-createdOrder.getId());

        // ACT
        Response fetchNotValidOrderId = orderClient.getOrderById(orderId);

        // ASSERT
        assertEquals(HttpStatus.SC_NOT_FOUND, fetchNotValidOrderId.getStatusCode());
        assertEquals(StatusConstant.STATUS_CODE, fetchNotValidOrderId.jsonPath().get("code").toString());
        assertEquals(StatusConstant.STATUS_CODE_TYPE, fetchNotValidOrderId.jsonPath().get("type").toString());
        assertEquals(StatusConstant.MESSAGE_NOT_FOUND, fetchNotValidOrderId.jsonPath().get("message").toString());
    }

}
