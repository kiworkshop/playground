package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findOne(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return UserResponse.convertFrom(user.orElseGet(User::new));
    }

    @Transactional
    public UserResponse save(UserRequest dto) {
        User user = userRepository.save(dto.toUser());
        return findOne(user.getId());
    }
}
