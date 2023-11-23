package com.triofantastico.practiceproject.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Errors(String code, String type, String message) {
}
