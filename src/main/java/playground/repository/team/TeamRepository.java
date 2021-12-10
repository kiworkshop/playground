package playground.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
