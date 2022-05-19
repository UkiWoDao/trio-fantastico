package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.constant.StatusConstant;
import com.triofantastico.practiceproject.httpclient.restful.PetClient;
import com.triofantastico.practiceproject.junit.annotations.WIP;
import com.triofantastico.practiceproject.model.pet.Pet;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(createdPet.getName(), desiredPet.getName());
        assertEquals(createdPet.getStatus(), desiredPet.getStatus());
        assertEquals(createdPet.getCategory(), desiredPet.getCategory());
        assertEquals(createdPet.getPhotoUrls(), desiredPet.getPhotoUrls());
        assertEquals(createdPet.getTags(), desiredPet.getTags());
    }

    @Test
    void deleting_existing_pet_should_returned_specified_response() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.createValidRandomPet();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Response fetchedPetResponse = petClient.delete(createdPet);
        Pet deleteFetchedPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, fetchedPetResponse.getStatusCode());
        Assertions.assertAll("Delete existing path should successfully retrieve this response",
                () -> assertEquals(StatusConstant.STATUS_CODE_OK, fetchedPetResponse.jsonPath().get("code").toString()),
                () -> assertEquals(StatusConstant.MESSAGE_TYPE, fetchedPetResponse.jsonPath().get("type").toString()),
                () -> assertEquals(deleteFetchedPet.getId().toString(), fetchedPetResponse.jsonPath().get("message").toString())
                            );
    }

    @Test
    void fetching_existing_pet_should_retrieve_specified_pet() throws JsonProcessingException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        PetClient petClient = new PetClient();
        Pet desiredPet = Pet.createValidRandomPet();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Response fetchedPetResponse = petClient.get(createdPet);
        Pet fetchedPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);

         // ASSERT
        assertEquals(HttpStatus.SC_OK, fetchedPetResponse.getStatusCode());
        assertEquals(createdPet.getName(), fetchedPet.getName());
        assertEquals(createdPet.getStatus(), fetchedPet.getStatus());
        assertEquals(createdPet.getCategory(), fetchedPet.getCategory());
        assertEquals(createdPet.getPhotoUrls(), fetchedPet.getPhotoUrls());
        assertEquals(createdPet.getTags(), fetchedPet.getTags());
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
        assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Pet desiredUpdatedPet = Pet.createValidRandomPet();
        desiredUpdatedPet.setId(createdPet.getId());

        Response createUpdatePetResponse = petClient.put(desiredUpdatedPet);
        Pet updatedPet = objectMapper.readValue(createUpdatePetResponse.getBody().asString(), Pet.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, createUpdatePetResponse.getStatusCode());
        assertEquals(desiredUpdatedPet.getId(), updatedPet.getId());
        assertEquals(desiredUpdatedPet.getName(), updatedPet.getName());
        assertEquals(desiredUpdatedPet.getStatus(), updatedPet.getStatus());
        assertEquals(desiredUpdatedPet.getCategory().getName(), updatedPet.getCategory().getName());
        assertEquals(desiredUpdatedPet.getPhotoUrls(), updatedPet.getPhotoUrls());
        assertEquals(desiredUpdatedPet.getTags(), updatedPet.getTags());
    }
}