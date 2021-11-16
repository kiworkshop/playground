package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.JdbcUserRepository;
import playground.domain.user.User;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JdbcUserRepository userRepository;

    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId);
        return UserResponse.convertFrom(user);
    }

    @Transactional
    public UserResponse save(UserRequest dto) {
        User user = User.builder()
                .name(dto.getName())
                .build();
        Long userId = userRepository.save(user);
        return findOne(userId);
    }
}
