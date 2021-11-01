package playground.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import playground.controller.request.CreateUserRequest;
import playground.domain.User;
import playground.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자를 저장한다.")
    void save() {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("a@naver.com", "Password123!", "김성빈");

        //when
        userService.save(createUserRequest);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }
}
