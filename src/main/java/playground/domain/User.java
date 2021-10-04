package playground.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {

    Long id;
    String name;
}
