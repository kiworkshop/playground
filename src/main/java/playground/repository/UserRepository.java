package playground.repository;

import learning.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

}
