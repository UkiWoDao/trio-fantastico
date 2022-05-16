package com.triofantastico.practiceproject.model.pet;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tag {

    private Integer id;
    private String name;

    public static Tag createRandomValidTag() {
        int tenThousand = 10000;
        return Tag.builder()
                .id(RandomGenerator.getRandomNumberPositiveNumberUpTo(tenThousand))
                .name("tagName" + RandomGenerator.getRandomNumberPositiveNumberUpTo(tenThousand))
                .build();
    }
}