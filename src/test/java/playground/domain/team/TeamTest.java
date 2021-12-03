package playground.domain.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTest {

    @Test
    @DisplayName("팀을 생성한다.")
    void create() {
        //given
        String name = "정산시스템팀";

        //when
        Team team = new Team(name);

        //then
        assertThat(team)
                .extracting("name")
                .isEqualTo(name);
    }
}
