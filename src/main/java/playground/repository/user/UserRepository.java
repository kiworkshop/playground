package playground.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
