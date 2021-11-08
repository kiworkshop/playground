package playground.service.user;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.controller.user.request.CreateUserRequest;
import playground.domain.user.User;
import playground.repository.user.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(final CreateUserRequest createUserRequest) {
        User user = createUserRequest.toUser();
        saveInDatabase(user);
    }

    private void saveInDatabase(final User user) {
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException(String.format("[%s] 이미 가입된 이메일입니다.", user.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAllById(final List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Transactional(readOnly = true)
    public User findById(final Long userId) {
        return userRepository.findById(userId);
    }
}
