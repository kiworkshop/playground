package learning;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder
public class User {

    @Getter
    private Long id;
    private String name;

}

