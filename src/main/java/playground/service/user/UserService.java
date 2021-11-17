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
        Long userId = userRepository.save(dto.toUser());
        return findOne(userId);
    }
}
