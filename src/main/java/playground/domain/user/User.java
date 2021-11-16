package playground.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private final Long id;
    private final String name;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

}
