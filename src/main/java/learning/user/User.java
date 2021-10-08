package learning.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = {"id"})
@Getter
@Builder
public class User {

    private Long id;
    private String name;
}
