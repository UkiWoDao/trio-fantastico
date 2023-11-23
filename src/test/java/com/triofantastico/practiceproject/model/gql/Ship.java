package com.triofantastico.practiceproject.model.gql;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Ship(
        String name,
        @JsonProperty("home_port")
        String homePort,
        String image) {
}
