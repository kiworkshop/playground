package playground.service.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.JobPosition;
import playground.domain.user.Team;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String password;
    private String email;
    private String teamName;
    private String jobPosition;

    public UserRequest(String name, String password, String email, String teamName, String jobPosition) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.teamName = teamName;
        this.jobPosition = jobPosition;
    }

    public User toUser(Team team, JobPosition jobPosition) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .team(team)
                .jobPosition(jobPosition)
                .build();
    }
}