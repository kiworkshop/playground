package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private static UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId);
    }
}
