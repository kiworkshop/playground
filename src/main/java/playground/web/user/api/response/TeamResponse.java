package playground.web.user.api.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.Team;

@Getter
@NoArgsConstructor
public class TeamResponse {

    private Long id;
    private String name;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }

}
