package playground.service.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectUserResponse {

    private Long id;
    private JobPosition jobPosition;
    private String teamName;
    private String name;
    private String jobPositionText;

    public SelectUserResponse(final User user) {
        this.id = user.getId();
        this.jobPosition = user.getJobPosition();
        this.teamName = user.getTeam().getName();
        this.name = user.getName();
        this.jobPositionText = user.getJobPosition().getText();
    }
}
