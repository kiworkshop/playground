package playground.domain.user;

public interface UserRepository {

    User findById(Long userId);
}
