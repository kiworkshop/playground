package playground.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.user.entity.User;
import playground.user.entity.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private static UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId);
    }
}
