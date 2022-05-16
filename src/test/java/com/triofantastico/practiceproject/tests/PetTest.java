package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.httpclient.restful.PetClient;
import com.triofantastico.practiceproject.model.pet.Category;
import com.triofantastico.practiceproject.model.pet.Tag;
import io.restassured.response.Response;
import com.triofantastico.practiceproject.model.pet.Pet;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
                .category(Category.builder()
                        .name("testCategoryUnique")
                        .build())
                .status("testStatusUnique")
                .photoUrls(new ArrayList<String>(List.of("testPhotoUrls")))
                .tags(new ArrayList<Tag>(List.of(
                        Tag.builder()
                                .name("testTagNameUnique")
                                .build()
                )))
                .build();

        Response createdPetResponse = petClient.add(desiredPet);
        Pet createdPet = objectMapper.readValue(createdPetResponse.getBody().asString(), Pet.class);
        Assertions.assertEquals(HttpStatus.SC_OK, createdPetResponse.getStatusCode());

        // ACT
        Random random = new Random();
        String randomStatus = "status" + random.nextInt(99999);

        Pet desiredUpdatedPet = Pet.builder()
                .id(createdPet.getId())
                .name(RandomStringUtils.randomAlphabetic(10))
                .category(Category.builder()
                        .name(RandomStringUtils.randomAlphabetic(10))
                        .build())
                .status(randomStatus)
                .photoUrls(new ArrayList<String>(List.of(RandomStringUtils.randomAlphabetic(10))))
                .tags(new ArrayList<Tag>(List.of(
                        Tag.builder()
                                .name(RandomStringUtils.randomAlphabetic(10))
                                .build()
                )))
                .build();

        Response createUpdatePetResponse = petClient.put(desiredUpdatedPet);
        Pet updateAnExistingPet = objectMapper.readValue(createUpdatePetResponse.getBody().asString(), Pet.class);

        // ASSERT
        Assertions.assertEquals(HttpStatus.SC_OK, createUpdatePetResponse.getStatusCode());
        Assertions.assertEquals(desiredUpdatedPet.getId(), updateAnExistingPet.getId());
        Assertions.assertEquals(desiredUpdatedPet.getName(), updateAnExistingPet.getName());
        Assertions.assertEquals(desiredUpdatedPet.getStatus(), updateAnExistingPet.getStatus());
        Assertions.assertEquals(desiredUpdatedPet.getCategory().getName(), updateAnExistingPet.getCategory().getName());
        Assertions.assertEquals(desiredUpdatedPet.getPhotoUrls().get(0), updateAnExistingPet.getPhotoUrls().get(0));
        Assertions.assertEquals(desiredUpdatedPet.getTags().get(0).getName(), updateAnExistingPet.getTags().get(0).getName());
    }
}