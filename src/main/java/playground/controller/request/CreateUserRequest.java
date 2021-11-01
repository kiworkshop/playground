package playground.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    public CreateUserRequest(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User toUser() {
        return new User(email, password, name);
    }
}
