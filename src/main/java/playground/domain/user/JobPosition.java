package playground.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobPosition {

    TEAM_LEADER("팀장"),
    PART_MANAGER("파트장"),
    TEAM_MEMBER("팀원");

    private final String text;

}
