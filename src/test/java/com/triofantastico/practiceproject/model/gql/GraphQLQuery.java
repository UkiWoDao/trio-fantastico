package com.triofantastico.practiceproject.model.gql;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLQuery {

    private String query;
    private Object variables;
}
