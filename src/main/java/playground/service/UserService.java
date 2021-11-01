package playground.service;

import org.springframework.stereotype.Service;
import playground.controller.request.CreateUserRequest;
import playground.domain.User;
import playground.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(final CreateUserRequest createUserRequest) {
        User user = createUserRequest.toUser();
        userRepository.save(user);
    }
}
