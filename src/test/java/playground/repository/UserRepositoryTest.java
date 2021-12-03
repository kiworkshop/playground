package playground.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.Document;
import playground.domain.Team;
import playground.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.domain.Rank.TEAM_MEMBER;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TeamRepositiory teamRepositiory;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("특정 팀의 모든 사용자를 조회한다.")
    @Test
    void findByTeam() {
        // given
        Team team1 = teamRepositiory.save(new Team("서비스개발팀"));
        Team team2 = teamRepositiory.save(new Team("서비스개발팀2"));
        User user1 = userRepository.save(new User("김우빈", "wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER, team1));
        User user2 = userRepository.save(new User("김우빈2", "wbluke2@abc.com", "p@ssw0rd", TEAM_MEMBER, team1));
        User user3 = userRepository.save(new User("김우빈3", "wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER, team2));
        User user4 = userRepository.save(new User("김우빈4", "wbluke2@abc.com", "p@ssw0rd", TEAM_MEMBER, team2));

        // when
        List<User> users = userRepository.findByTeam(team1);

        // then
        assertThat(users).hasSize(2)
                .extracting("name")
                .contains("김우빈", "김우빈2");
    }

}
