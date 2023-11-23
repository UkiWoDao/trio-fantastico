package com.triofantastico.practiceproject.model.pet;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
        int upperLimit = 10000;
        return Category.builder()
                .id(RandomGenerator.getRandomPositiveNumberUpTo(upperLimit))
                .name("categoryName" + RandomGenerator.getRandomPositiveNumberUpTo(upperLimit))
                .build();
    }
}