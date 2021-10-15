package playground.user.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private Long id;
    private String name;
}
