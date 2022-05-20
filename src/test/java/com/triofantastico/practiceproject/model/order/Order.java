package com.triofantastico.practiceproject.model.order;

import com.triofantastico.practiceproject.helper.RandomGenerator;
import lombok.*;
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

    private static final int MAX_INT = 1000;
    private static final int MAX_DAYS = 30;

    public static Order createValidRandomOrder() {
        final int upToThirty = RandomGenerator.getRandomPositiveNumberUpTo(30);
        final String futureDatetimeInNextThirtyDays = LocalDateTime.now().plusDays(upToThirty)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        return Order.builder()
                .petId(Long.valueOf(RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT)))
                .quantity(Long.valueOf(RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT)))
                .shipDate(futureDatetimeInNextThirtyDays)
                .status("status" + RandomGenerator.getRandomPositiveNumberUpTo(MAX_INT))
                .complete(nextBoolean())
                .build();
    }
}
