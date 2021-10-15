package playground.service;

import learning.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findOne() {
        //given

        //when
        User user = userService.findOne(1L).get();

        //then
        assertThat(user.getName()).isEqualTo("user1");
    }

    @Test
    void save() {
        //given
        User user = User.builder().name("test").build();

        //when
        User save = userService.save(user);

        //then
        assertThat(save.getName()).isEqualTo("test");
    }
}