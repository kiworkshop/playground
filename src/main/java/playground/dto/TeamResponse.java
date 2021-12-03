package playground.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import playground.domain.Team;

@NoArgsConstructor
@Data
public class TeamResponse {

    private Long id;

    private String name;

    public TeamResponse(Team team){
        this.id = team.getId();
        this.name = team.getName();
    }
}
