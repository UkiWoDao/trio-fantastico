package com.triofantastico.practiceproject.model.gql;

import java.util.UUID;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    private UUID id;
    private String name;
    private String rocket;

    private static final int THOUSAND = 1000;

    public static User createValidRandomUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("name" + RandomGenerator.getRandomPositiveNumberUpTo(THOUSAND))
                .rocket("rocket" + RandomGenerator.getRandomPositiveNumberUpTo(THOUSAND))
                .build();
    }
}
