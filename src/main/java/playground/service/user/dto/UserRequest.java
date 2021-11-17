package playground.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;

    @Builder
    public UserRequest(String name) {
        this.name = name;
    }

    public User toUser() {
        return User.builder()
                .name(name)
                .build();
    }
}