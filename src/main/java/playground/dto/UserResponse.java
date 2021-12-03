package playground.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import playground.domain.User;

@Data
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String jobPosition;

    private String teamName;

    private String name;

    private String jobPositionText;

    public UserResponse(User user) {
        this.id = user.getId();
        this.jobPosition = user.getRank().toString();
        this.teamName = user.getTeam().getName();
        this.name = user.getName();
        this.jobPositionText = user.getRank().getName();
    }
}
