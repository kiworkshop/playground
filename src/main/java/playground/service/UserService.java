package playground.service;

import learning.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.dto.UserRequest;
import playground.dto.UserResponse;
import playground.repository.JdbcUserRepository;

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
