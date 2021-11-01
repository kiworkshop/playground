package playground.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        //given
        User user = new User("a@naver.com", "password", "김성빈");

        //when
        long userId = userRepository.save(user);

        //then
        assertThat(userId).isNotZero();
    }

    @Test
    @DisplayName("중복된 이메일일 경우, 예외가 발생한다.")
    void save_fail_duplicated_email() {
        //given
        User user1 = new User("a@naver.com", "password", "김성빈");
        userRepository.save(user1);
        User user2 = new User("a@naver.com", "password2", "김성빈2");

        //when, then
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DuplicateKeyException.class);
    }
}
