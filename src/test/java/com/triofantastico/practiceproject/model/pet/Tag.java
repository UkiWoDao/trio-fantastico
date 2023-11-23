package com.triofantastico.practiceproject.model.pet;

import com.triofantastico.practiceproject.helper.RandomGenerator;

public record Tag(Integer id, String name) {

    public static Tag createRandomValidTag() {
        int tenThousand = 10000;
        Integer someValidRandomId = RandomGenerator.getRandomPositiveNumberUpTo(tenThousand);
        String someValidRandomName = "tagName" + RandomGenerator.getRandomPositiveNumberUpTo(tenThousand);

        return new Tag(someValidRandomId, someValidRandomName);
    }
}
