package playground.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.JobPosition;
import playground.domain.user.Team;
import playground.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TeamUserResponse {
    private Long id;
    private JobPosition jobPosition;
    private String teamName;
    private String name;
    private String jobPositionText;

    public static TeamUserResponse of(User user, Team team) {
        return TeamUserResponse.builder()
                .id(user.getId())
                .jobPosition(user.getJobPosition())
                .teamName(team.getName())
                .name(user.getName())
                .build();
    }

    public static List<TeamUserResponse> ofList(List<User> users, Team team) {
        return users.stream()
                .map(user -> of(user, team))
                .collect(Collectors.toList());
    }

    public String getJobPositionText() {
        return jobPosition.getText();
    }
}
