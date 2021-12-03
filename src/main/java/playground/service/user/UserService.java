package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.exception.NotFoundException;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse saveUser(UserRequest dto) {
        User user = userRepository.save(dto.toUser());
        return UserResponse.of(user);
    }

    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        return UserResponse.of(user);
    }
}
