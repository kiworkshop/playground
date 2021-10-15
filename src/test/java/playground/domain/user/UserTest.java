package playground.domain.user;

import fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("사용자를 생성한다.")
    void create() {
        //given
        long id = 1L;
        String name = "사용자";

        //when
        User user = UserFixture.create(id, name);

        //then
        assertThat(user).extracting("id", "name")
                .containsExactly(id, name);
    }

    @Test
    @DisplayName("같은 사용자인지 확인한다.")
    void isSame() {
        //given
        User user1 = UserFixture.create(1L, "사용자1");
        User user2 = UserFixture.create(2L, "사용자2");

        //when, then
        assertThat(user1.isSame(user1)).isTrue();
        assertThat(user1.isSame(user2)).isFalse();
    }
}
