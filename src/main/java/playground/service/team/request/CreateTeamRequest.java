package playground.service.team.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTeamRequest {

    @NotBlank
    private String name;

    public CreateTeamRequest(final String name) {
        this.name = name;
    }

    public Team toTeam() {
        return new Team(name);
    }
}
