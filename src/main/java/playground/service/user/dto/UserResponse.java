package playground.service.user.dto;

import lombok.Getter;
import playground.domain.user.JobPosition;
import playground.domain.user.User;

@Getter
public class UserResponse {

    private Long id;
    private JobPosition jobPosition;
    private String teamName;
    private String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.jobPosition = user.getJobPosition();
        this.teamName = user.getTeamName();
        this.name = user.getName();
    }

    public String getJobPositionText() {
        return jobPosition.getText();
    }

}
