package playground.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.controller.request.CreateUserRequest;
import playground.domain.User;
import playground.repository.UserRepository;

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
    public User findById(final Long userId) {
        User user = userRepository.findById(userId);
        checkExistence(userId, user);
        return user;
    }

    private void checkExistence(final long userId, final User user) {
        if (user == null) {
            throw new IllegalArgumentException(String.format("[%d] 식별번호와 일치하는 회원이 존재하지 않습니다.", userId));
        }
    }
}
