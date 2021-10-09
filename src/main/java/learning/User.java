package learning;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
@Getter
public class User {

    private Long id;
    private String name;

    @Builder
    private User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
