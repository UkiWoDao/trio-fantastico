package com.triofantastico.practiceproject.model.gql;

import java.util.List;
import java.util.Map;

public record Data(Map<String, List<Company>> data) {
}
