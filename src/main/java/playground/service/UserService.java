package playground.service;

import learning.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.repository.JdbcUserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final JdbcUserRepository userRepository;

    @Autowired
    public UserService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
