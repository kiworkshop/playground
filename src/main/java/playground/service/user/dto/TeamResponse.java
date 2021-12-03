package playground.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.Team;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TeamResponse {
    private Long id;
    private String name;

    public static TeamResponse of(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public static List<TeamResponse> ofList(List<Team> teams) {
        return teams.stream()
                .map(TeamResponse::of)
                .collect(Collectors.toList());
    }
}
