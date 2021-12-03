package playground.service.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;

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

    @NotBlank
    private String jobPosition;

    public CreateUserRequest(final String email, final String password, final String name,
                             final String teamName, final String jobPosition) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.teamName = teamName;
        this.jobPosition = jobPosition;
    }

    public User toUser(final Team team) {
        return new User(email, password, name, team, JobPosition.valueOf(jobPosition));
    }
}
