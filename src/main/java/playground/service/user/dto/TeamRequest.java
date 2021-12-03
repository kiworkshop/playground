package playground.service.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.Team;

@Getter
@NoArgsConstructor
public class TeamRequest {

    private String name;

    public TeamRequest(String name) {
        this.name = name;
    }

    public Team toTeam() {
        return new Team(name);
    }
}
