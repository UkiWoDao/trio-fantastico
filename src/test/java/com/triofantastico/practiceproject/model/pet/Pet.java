package com.triofantastico.practiceproject.model.pet;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private ArrayList<String> photoUrls;
    private ArrayList<Tag> tags;
    private String status;
    private static final int MAX_INT = 1000;

    public static Pet createValidRandomPet() {
        int upToFive = RandomGenerator.getRandomPositiveNumberUpTo(5);
        return Pet.builder()
                .name("name" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT))
                .category(Category.createRandomValidCategory())
                .status("status" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT))
                .photoUrls(getANumberOfRandomUrls(upToFive))
                .tags(getANumberOfRandomTags(upToFive))
                .build();
    }

     private static ArrayList<String> getANumberOfRandomUrls(int count) {
        ArrayList<String> listOfUrls = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            listOfUrls.add("https://petdomain.com/" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT));
        }
        return listOfUrls;
     }

    private static ArrayList<Tag> getANumberOfRandomTags(int count) {
        ArrayList<Tag> listOfTags = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            listOfTags.add(Tag.createRandomValidTag());
        }
        return listOfTags;
    }
}