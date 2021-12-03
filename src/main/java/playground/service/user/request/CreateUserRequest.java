package playground.service.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;
import playground.domain.user.User;

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

    @NotBlank
    private String teamName;

    public CreateUserRequest(final String email, final String password, final String name, final String teamName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.teamName = teamName;
    }

    public User toUser(final Team team) {
        return new User(email, password, name, team);
    }
}
