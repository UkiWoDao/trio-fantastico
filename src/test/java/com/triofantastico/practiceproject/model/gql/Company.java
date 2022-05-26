package com.triofantastico.practiceproject.model.gql;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String name;
    private String ceo;
    private String coo;
}
