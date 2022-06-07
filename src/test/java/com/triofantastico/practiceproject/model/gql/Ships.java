package com.triofantastico.practiceproject.model.gql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Ships {

    private List<Ship> ships;
    @JsonProperty("launch_date_local")
    private String launchDateLocal;
}