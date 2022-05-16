package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.helper.RandomGenerator;
import com.triofantastico.practiceproject.httpclient.restful.PetClient;
import com.triofantastico.practiceproject.junit.annotations.WIP;
import com.triofantastico.practiceproject.model.pet.Pet;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PetTest {

    @Test
    void adding_new_valid_pet_should_create_pet_as_intended() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.createValidRandomPet();

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
        Pet desiredPet = Pet.createValidRandomPet();

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

    @WIP
    @Test
    void update_existing_pet_should_retrieve_pet_with_new_values() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.createValidRandomPet();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        Assertions.assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Pet desiredUpdatedPet = Pet.createValidRandomPet();
        desiredUpdatedPet.setId(createdPet.getId());

        Response createUpdatePetResponse = petClient.put(desiredUpdatedPet);
        Pet updatedPet = objectMapper.readValue(createUpdatePetResponse.getBody().asString(), Pet.class);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, createUpdatePetResponse.getStatusCode());
        Assertions.assertEquals(desiredUpdatedPet.getId(), updatedPet.getId());
        Assertions.assertEquals(desiredUpdatedPet.getName(), updatedPet.getName());
        Assertions.assertEquals(desiredUpdatedPet.getStatus(), updatedPet.getStatus());
        Assertions.assertEquals(desiredUpdatedPet.getCategory().getName(), updatedPet.getCategory().getName());
        Assertions.assertEquals(desiredUpdatedPet.getPhotoUrls(), updatedPet.getPhotoUrls());
        Assertions.assertEquals(desiredUpdatedPet.getTags(), updatedPet.getTags());
    }
}