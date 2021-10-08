package learning;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private final Long id;
    private final String name;

}
