package playground.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("사용자를 생성한다.")
    void create() {
        //given
        String email = "seongbeen93@naver.com";
        String password = "password";
        String name = "김성빈";

        //when
        User user = new User(email, password, name);

        //then
        assertThat(user)
                .extracting("email", "password", "name")
                .containsExactly(email, password, name);
    }
}
