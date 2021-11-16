package playground.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

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
        UserResponse user = userService.findOne(1L);

        //then
        assertThat(user.getName()).isEqualTo("user1");
    }

    @Test
    void save() {
        //given
        UserRequest requestDto = UserRequest.builder().name("test").build();

        //when
        UserResponse save = userService.save(requestDto);

        //then
        assertThat(save.getName()).isEqualTo("test");
    }
}