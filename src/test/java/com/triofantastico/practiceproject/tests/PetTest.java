package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.httpclient.restful.PetClient;
import io.restassured.response.Response;
import com.triofantastico.practiceproject.model.pet.Pet;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PetTest {

    @Test
    void adding_new_valid_pet_should_create_pet_as_intended() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.builder()
                .name("testName")
                .status("testStatus")
                .build();

        // ACT
        Response response = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(response.getBody().asString(), Pet.class);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(createdPet.getName(), desiredPet.getName());
        Assertions.assertEquals(createdPet.getStatus(), desiredPet.getStatus());
    }
}