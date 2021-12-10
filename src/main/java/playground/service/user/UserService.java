package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.Team;
import playground.domain.user.TeamRepository;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public User getById(Long userId) {
        return findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 사용자입니다. userId = %s", userId)));
    }

    public List<User> findAllById(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public Optional<Team> findTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
}
