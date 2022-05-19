package com.triofantastico.practiceproject.httpclient.restful;

import com.triofantastico.practiceproject.config.propertybased.ConfigurationManager;
import io.restassured.http.Method;
import io.restassured.response.Response;
import com.triofantastico.practiceproject.model.pet.Pet;

import java.util.Map;

public class PetClient extends RestfulClient {

    private static final String PET_URI = PETSTORE_BASE_URL + "/pet";
    private final Map<String, String> apiKeyHeader = Map.of("api_key", ConfigurationManager.getAuthConfigInstance().apikey());

    public Response add(Pet pet) { return sendRequest(getCommonReqSpec().baseUri(PET_URI).body(pet), Method.POST); }
    public Response get(Pet pet) { return sendRequest(getCommonReqSpec().baseUri(PET_URI + "/" + pet.getId()), Method.GET); }

    public Response delete(Pet pet) { return sendRequest(getCommonReqSpec().headers(apiKeyHeader)
            .baseUri(PET_URI + "/" + pet.getId()), Method.DELETE); }

    public Response put(Pet pet) { return sendRequest(getCommonReqSpec().baseUri(PET_URI).body(pet), Method.PUT); }
}