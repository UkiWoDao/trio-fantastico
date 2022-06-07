package com.triofantastico.practiceproject.model.gql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Ship {

    private String name;
    @JsonProperty("home_port")
    private String homePort;
    private String image;

}
