package com.triofantastico.practiceproject.model.gql;

import java.util.ArrayList;
import java.util.UUID;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends ArrayList<User> {

    private UUID id;
    private String name;
    private String rocket;

    private static final int MAX_INT = 1000;

    public static User createValidRandomUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("name" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT))
                .rocket("rocket" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT))
                .build();
    }
}
