package com.triofantastico.practiceproject.model.order;

import com.triofantastico.practiceproject.helper.RandomGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long petId;
    private Long quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public static Order createValidRandomOrder() {
        int upperNumberLimit = 1000;

        final int upToThirty = RandomGenerator.getRandomPositiveNumberUpTo(30);
        final String futureDatetimeInNextThirtyDays = LocalDateTime.now().plusDays(upToThirty)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        return Order.builder()
                .petId(Long.valueOf(RandomGenerator.getRandomPositiveNumberUpTo(upperNumberLimit)))
                .quantity(Long.valueOf(RandomGenerator.getRandomPositiveNumberUpTo(upperNumberLimit)))
                .shipDate(futureDatetimeInNextThirtyDays)
                .status("status" + RandomGenerator.getRandomPositiveNumberUpTo(upperNumberLimit))
                .complete(nextBoolean())
                .build();
    }
}
