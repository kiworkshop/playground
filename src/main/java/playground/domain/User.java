package playground.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    Long id;
    String name;
}
