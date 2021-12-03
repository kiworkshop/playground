package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.Team;

public interface TeamRepositiory extends JpaRepository<Team, Long> {
}
