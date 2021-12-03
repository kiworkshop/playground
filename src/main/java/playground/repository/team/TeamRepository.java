package playground.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.team.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(final String teamName);
}
