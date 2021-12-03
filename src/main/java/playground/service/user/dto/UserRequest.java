package playground.service.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String password;
    private String email;

    public UserRequest(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User toUser() {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}