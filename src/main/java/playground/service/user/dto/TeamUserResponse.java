package playground.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.JobPosition;
import playground.domain.user.Team;
import playground.domain.user.User;

@Getter
@Builder
public class TeamUserResponse {
    private Long id;
    private JobPosition jobPosition;
    private String teamName;
    private String name;
    private String jobPositionText;

    public static TeamUserResponse convertFrom(User user, Team team, JobPosition jobPosition) {
        return TeamUserResponse.builder()
                .id(user.getId())
                .jobPosition(jobPosition)
                .teamName(team.getName())
                .name(user.getName())
                .build();
    }

    public String getJobPositionText() {
        return jobPosition.getText();
    }
}
