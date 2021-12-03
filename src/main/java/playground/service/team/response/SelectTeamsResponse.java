package playground.service.team.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectTeamsResponse {

    private List<SelectTeamResponse> selectTeamResponses;

    public SelectTeamsResponse(final List<Team> teams) {
        this.selectTeamResponses = teams.stream()
                .map(SelectTeamResponse::new)
                .collect(Collectors.toList());
    }
}
