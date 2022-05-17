package com.triofantastico.practiceproject.httpclient.restful;

import com.triofantastico.practiceproject.model.order.Order;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OrderClient extends RestfulClient
{
    private static final String ORDER_URI = PETSTORE_BASE_URL + "/store";

    public Response post(Order order) { return sendRequest(getCommonReqSpec().baseUri(ORDER_URI + "/" + "order").body(order), Method.POST); }

    public Response getInventory() { return sendRequest(getCommonReqSpec().baseUri(ORDER_URI + "/" + "inventory"), Method.GET); }

    public Response getOrderById(Integer orderId) { return sendRequest(getCommonReqSpec().baseUri(ORDER_URI + "/" + "order" + "/" + orderId), Method.GET); }

    public Response delete(Integer orderId) { return sendRequest(getCommonReqSpec().baseUri(ORDER_URI + "/" + "order" + "/" + orderId), Method.DELETE); }
}
