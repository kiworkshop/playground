package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
