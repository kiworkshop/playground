package playground.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRespositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("user1"));
    }

    @Test
    void findById() {
        //when
        Optional<User> findUser = userRepository.findById(user.getId());

        //then
        assertThat(findUser.isPresent()).isTrue();
    }

    @Test
    void save() {
        //then
        assertThat(user.getName()).isEqualTo("user1");
    }
}