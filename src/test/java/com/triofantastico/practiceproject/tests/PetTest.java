package com.triofantastico.practiceproject.tests;

import com.triofantastico.practiceproject.httpclient.restful.PetClient;
import io.restassured.response.Response;
import com.triofantastico.practiceproject.model.pet.Pet;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PetTest {

    @Test
    void adding_new_valid_pet_should_succeed() {
        // ARRANGE
        PetClient petClient = new PetClient();
        Pet pet = Pet.builder()
                .name("testName")
                .status("testStatus")
                .build();

        // ACT
        Response response = petClient.add(pet);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }
}