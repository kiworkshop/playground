package playground.service.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectUsersInTeamResponse {

    private List<SelectUserResponse> selectUserResponses;

    public SelectUsersInTeamResponse(final List<User> users) {
        this.selectUserResponses = users.stream()
                .map(SelectUserResponse::new)
                .collect(Collectors.toList());
    }
}
