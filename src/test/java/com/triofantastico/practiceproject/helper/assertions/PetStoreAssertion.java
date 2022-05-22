package com.triofantastico.practiceproject.helper.assertions;

import com.triofantastico.practiceproject.model.pet.Pet;
import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetStoreAssertion extends AbstractAssert<PetStoreAssertion, Pet> {

    protected PetStoreAssertion(Pet actual) {
        super(actual, PetStoreAssertion.class);
    }

    public static PetStoreAssertion assertThat(Pet actual) {
        return new PetStoreAssertion(actual);
    }

    public PetStoreAssertion hasMatchingFieldValuesAs(Pet expectedPet) {
        isNotNull();

        Assertions.assertAll("Pet object should be handled as intended",
                () -> assertEquals(expectedPet.getName(), actual.getName()),
                () -> assertEquals(expectedPet.getStatus(), actual.getStatus()),
                () -> assertEquals(expectedPet.getCategory().getName(), actual.getCategory().getName()),
                () -> assertEquals(expectedPet.getPhotoUrls(), actual.getPhotoUrls()),
                () -> assertEquals(expectedPet.getTags(), actual.getTags()));

        return this;
    }

    public void andAGeneratedId() {
        Assertions.assertNotNull(actual.getId());
    }
}