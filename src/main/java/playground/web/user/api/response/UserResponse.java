package playground.web.user.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.JobPosition;
import playground.domain.user.Team;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private JobPosition jobPosition;
    private String teamName;
    private String name;

    public UserResponse(User user, Team team) {
        this.id = user.getId();
        this.jobPosition = user.getJobPosition();
        this.teamName = team.getName();
        this.name = user.getName();
    }

    public String getJobPosition() {
        return jobPosition.name();
    }

    public String getJobPositionText() {
        return jobPosition.getText();
    }

}
