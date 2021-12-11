package playground.service.team.dto;

import lombok.Getter;
import playground.domain.team.Team;

@Getter
public class TeamResponse {

    private final Long id;
    private final String name;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }

}
