package com.triofantastico.practiceproject.model.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Errors {

    private String code;
    private String type;
    private String message;
}
