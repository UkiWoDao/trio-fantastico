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

    @Test
    void fetching_existing_pet_should_retrieve_specified_pet() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.builder()
                .name("testName")
                .status("testStatus")
                .build();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        Assertions.assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());
        // Assumptions.assumeTrue(createdPetResponse.getStatusCode() == HttpStatus.SC_OK);

        // ACT
        Response fetchedPetResponse = petClient.get(createdPet);
        Pet fetchedPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);

         // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, fetchedPetResponse.getStatusCode());
        Assertions.assertEquals(createdPet.getName(), fetchedPet.getName());
        Assertions.assertEquals(createdPet.getStatus(), fetchedPet.getStatus());
    }

    @Test
    void update_existing_pet_should_retrieve_pet_with_new_values() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.builder()
                .name("testNameUnique")
                .status("testStatusUnique")
                .build();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        Assertions.assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Pet desiredUpdatedPet = Pet.builder()
                .id(createdPet.getId())
                .name("testNameUpdated")
                .status("testStatusUpdated")
                .build();

        Response createUpdatePetResponse = petClient.put(desiredUpdatedPet);
        Pet updateAnExistingPet = objectMapper.readValue(createUpdatePetResponse.getBody().asString(), Pet.class);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, createUpdatePetResponse.getStatusCode());
        Assertions.assertEquals(desiredUpdatedPet.getId(), updateAnExistingPet.getId());
        Assertions.assertEquals(desiredUpdatedPet.getName(), updateAnExistingPet.getName());
        Assertions.assertEquals(desiredUpdatedPet.getStatus(), updateAnExistingPet.getStatus());
    }
}