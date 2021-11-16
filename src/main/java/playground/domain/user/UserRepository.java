package playground.domain.user;

public interface UserRepository {

    User findById(Long id);

    Long save(User user);

}
