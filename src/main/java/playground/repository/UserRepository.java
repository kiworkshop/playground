package playground.repository;

import learning.User;

public interface UserRepository {

    User findById(Long id);

    Long save(User user);

}
