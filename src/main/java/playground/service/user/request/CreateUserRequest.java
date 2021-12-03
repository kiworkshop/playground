package playground.service.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private Long teamId;

    @NotBlank
    private String jobPosition;

    public CreateUserRequest(final String email, final String password, final String name,
                             final Long teamId, final String jobPosition) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.teamId = teamId;
        this.jobPosition = jobPosition;
    }

    public User toUser(final Team team) {
        return new User(email, password, name, team, JobPosition.valueOf(jobPosition));
    }
}
