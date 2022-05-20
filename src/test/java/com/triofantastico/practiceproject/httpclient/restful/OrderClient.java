package com.triofantastico.practiceproject.httpclient.restful;

import com.triofantastico.practiceproject.model.order.Order;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OrderClient extends RestfulClient
{
    private static final String STORE_URL = PET_STORE_BASE_URL + "/store";
    private static final String STORE_ORDER_URL = STORE_URL + "/order/";

    public Response create(Order order) { return sendRequest(getCommonReqSpec().baseUri(STORE_URL + "/" + "order").body(order), Method.POST); }
    public Response getInventory() { return sendRequest(getCommonReqSpec().baseUri(STORE_URL + "/" + "inventory"), Method.GET); }
    public Response getOrderById(Long orderId) { return sendRequest(getCommonReqSpec().baseUri(STORE_ORDER_URL + orderId), Method.GET); }
    public Response deleteOrderById(Long orderId) { return sendRequest(getCommonReqSpec().baseUri(STORE_ORDER_URL + orderId), Method.DELETE); }
}
