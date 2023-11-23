package com.triofantastico.practiceproject.model.gql;

import java.util.UUID;

import com.triofantastico.practiceproject.helper.RandomGenerator;

public record User(UUID id, String name, String rocket) {

    public static User createValidRandomUser() {
        int upperLimit = 1000;
        String someRandomValidName = "name" + RandomGenerator.getRandomPositiveNumberUpTo(upperLimit);
        String someValidRocket = "rocket" + RandomGenerator.getRandomPositiveNumberUpTo(upperLimit);

        return new User(UUID.randomUUID(), someRandomValidName, someValidRocket);
    }
}
