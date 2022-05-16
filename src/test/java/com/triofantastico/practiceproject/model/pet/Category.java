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
public class Category {

    private Integer id;
    private String name;

    public static Category createRandomValidCategory() {
        int tenThousand = 10000;
        return Category.builder()
                .id(RandomGenerator.getRandomNumberPositiveNumberUpTo(tenThousand))
                .name("categoryName" + RandomGenerator.getRandomNumberPositiveNumberUpTo(tenThousand))
                .build();
    }
}