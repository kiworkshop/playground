package playground.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.team.Team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UserTest {

    @Test
    @DisplayName("사용자를 생성한다.")
    void create() {
        //given
        Team team = mock(Team.class);
        String email = "seongbeen93@naver.com";
        String password = "password";
        String name = "김성빈";

        //when
        User user = new User(email, password, name, team);

        //then
        assertThat(user)
                .extracting("email", "password", "name", "team")
                .containsExactly(email, password, name, team);
    }
}
