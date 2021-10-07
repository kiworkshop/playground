package learning;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private final long id;
    private final String name;
}
