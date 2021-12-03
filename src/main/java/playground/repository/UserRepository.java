package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.Team;
import playground.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByTeam(Team team);
}
