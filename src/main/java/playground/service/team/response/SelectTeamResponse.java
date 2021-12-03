package playground.service.team.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectTeamResponse {

    private Long id;
    private String name;

    public SelectTeamResponse(final Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
