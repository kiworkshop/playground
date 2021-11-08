package playground.repository.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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

    @Test
    @DisplayName("식별번호에 일치하는 모든 사용자를 조회한다.")
    void findAllById() {
        //given
        User user1 = new User("a1@naver.com", "password", "김성빈1");
        User user2 = new User("a2@naver.com", "password2", "김성빈2");
        Long userId1 = userRepository.save(user1);
        Long userId2 = userRepository.save(user2);

        //when
        List<User> users = userRepository.findAllById(Arrays.asList(userId1, userId2));

        //then
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("식별번호에 일치하는 사용자를 조회한다.")
    void findById() {
        //given
        String email = "a1@naver.com";
        String password = "password";
        String name = "김성빈1";
        User user = new User(email, password, name);
        Long userId = userRepository.save(user);

        //when
        User fetchedUser = userRepository.findById(userId);

        //then
        assertThat(fetchedUser)
                .extracting("email", "password", "name")
                .containsExactly(email, password, name);
    }

    @Test
    @DisplayName("식별번호에 일치하는 사용자가 존재하지 않을 경우, 예외가 발생한다.")
    void findById_fail_empty_result() {
        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> userRepository.findById(0L))
                .withMessageContaining("해당하는 회원이 존재하지 않습니다.");
    }
}
