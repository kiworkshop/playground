package playground.domain.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    @DisplayName("식별자로 동등성을 비교한다.")
    void equality_with_id() {
        // given
        Long id = 1L;
        User user_one = User.builder()
                .id(id)
                .name("이름1")
                .build();
        User user_two = User.builder()
                .id(id)
                .name("이름2")
                .build();

        // when
        boolean result = user_one.equals(user_two);

        // then
        assertThat(result).isTrue();
    }
}
