package com.triofantastico.practiceproject.model.gql;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LaunchesPast {

    private List<Ships> shipsList;
}
