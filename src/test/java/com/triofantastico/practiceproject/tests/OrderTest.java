package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.constant.ResponseConstant;
import com.triofantastico.practiceproject.httpclient.restful.OrderClient;
import com.triofantastico.practiceproject.model.order.Order;
import com.triofantastico.practiceproject.model.responses.Errors;
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
        Response response = orderClient.create(desiredOrder);
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

        Response response = orderClient.create(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);

        // ACT
        Response deleteResponse = orderClient.deleteOrderById(createdOrder.getId());
        Errors errorResponse = objectMapper.readValue(deleteResponse.getBody().asString(), Errors.class);


        // ASSERT
        assertEquals(HttpStatus.SC_OK, deleteResponse.getStatusCode());
        Assertions.assertAll("Delete existing pet should successfully return specified response",
                () -> assertEquals(String.valueOf(HttpStatus.SC_OK), errorResponse.getCode()),
                () -> assertEquals(ResponseConstant.UNKNOWN, errorResponse.getType()),
                () -> assertEquals(createdOrder.getId().toString(), errorResponse.getMessage())
            );
    }

    @Test
    void attempting_to_delete_order_with_negative_id_should_return_specified_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.create(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Long invalidOrderId = -createdOrder.getId();

        // ACT
        Response deleteResponse = orderClient.deleteOrderById(invalidOrderId);
        Errors errorResponse = objectMapper.readValue(deleteResponse.getBody().asString(), Errors.class);

        // ASSERT
        assertEquals(HttpStatus.SC_NOT_FOUND, deleteResponse.getStatusCode());
        assertEquals(String.valueOf(HttpStatus.SC_NOT_FOUND), errorResponse.getCode());
        assertEquals(ResponseConstant.UNKNOWN, errorResponse.getType());
        assertEquals(ResponseConstant.ORDER_NOT_FOUND, errorResponse.getMessage());
    }

    @Test
    void fetching_valid_purchase_order_by_id_should_retrieve_specified_order() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.create(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        String shipJsonDate = createdOrder.getShipDate();

        // ACT
        Response fetchValidOrderId = orderClient.getOrderById(createdOrder.getId());

        // ASSERT
        assertEquals(HttpStatus.SC_OK, fetchValidOrderId.getStatusCode());
        assertEquals(createdOrder.getPetId(), desiredOrder.getPetId());
        assertEquals(createdOrder.getQuantity(), desiredOrder.getQuantity());
        assertEquals(shipJsonDate.replaceFirst(".{5}$", "Z"), desiredOrder.getShipDate());
        assertEquals(createdOrder.getStatus(), desiredOrder.getStatus());
        assertEquals(createdOrder.getComplete(), desiredOrder.getComplete());
    }

    @Test
    void attempting_to_get_order_by_invalid_id_should_return_an_error_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        OrderClient orderClient = new OrderClient();
        Order desiredOrder = Order.createValidRandomOrder();

        Response response = orderClient.create(desiredOrder);
        Order createdOrder = objectMapper.readValue(response.getBody().asString(), Order.class);
        Long invalidOrderId = -createdOrder.getId();

        // ACT
        Response invalidGetOrderById = orderClient.getOrderById(invalidOrderId);
        Errors errorResponse = objectMapper.readValue(invalidGetOrderById.getBody().asString(), Errors.class);

        // ASSERT
        assertEquals(HttpStatus.SC_NOT_FOUND, invalidGetOrderById.getStatusCode());
        assertEquals(ResponseConstant.ONE, errorResponse.getCode());
        assertEquals(ResponseConstant.ERROR, errorResponse.getType());
        assertEquals(ResponseConstant.ORDER_NOT_FOUND.toLowerCase(), errorResponse.getMessage().toLowerCase());
    }
}
