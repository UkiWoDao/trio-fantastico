package com.triofantastico.practiceproject.model.gql;

import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    private Map<String, List<Company>> data;
}
