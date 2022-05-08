package com.triofantastico.practiceproject.httpclient.restful;

import io.restassured.http.Method;
import io.restassured.response.Response;
import com.triofantastico.practiceproject.model.pet.Pet;

public class PetClient extends RestfulClient {

    private static final String PET_URI = PETSTORE_BASE_URL + "/pet";

    public Response add(Pet pet) {
        return sendRequest(getCommonReqSpec().baseUri(PET_URI).body(pet), Method.POST);
    }
}