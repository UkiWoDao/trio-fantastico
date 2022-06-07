package com.triofantastico.practiceproject.model.gql;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Launch {

    private Date launch_date_local;
}
