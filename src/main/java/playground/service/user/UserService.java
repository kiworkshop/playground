package playground.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.domain.user.JdbcUserRepository;
import playground.domain.user.User;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

@Service
public class UserService {

    private final JdbcUserRepository userRepository;

    @Autowired
    public UserService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId);
        return UserResponse.convertFrom(user);
    }

    public UserResponse save(UserRequest dto) {
        User user = User.builder()
                .name(dto.getName())
                .build();
        Long userId = userRepository.save(user);
        return findOne(userId);
    }
}
