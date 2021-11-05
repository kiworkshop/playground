package playground.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.user.entity.User;
import playground.user.entity.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private static UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId);
    }
}
