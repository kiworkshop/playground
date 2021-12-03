package playground.service.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.repository.user.UserRepository;
import playground.service.team.TeamService;
import playground.service.user.request.CreateUserRequest;
import playground.service.user.response.SelectAllUserInTeamResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeamService teamService;

    public UserService(final UserRepository userRepository, final TeamService teamService) {
        this.userRepository = userRepository;
        this.teamService = teamService;
    }

    @Transactional
    public void create(final CreateUserRequest createUserRequest) {
        Team team = teamService.findById(createUserRequest.getTeamId());
        User user = createUserRequest.toUser(team);
        save(user);
    }

    private void save(final User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(String.format("[%s] 이미 가입된 이메일입니다.", user.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAllById(final List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        checkEmpty(userIds, users);

        return users;
    }

    private void checkEmpty(final List<Long> userIds, final List<User> users) {
        if (users.isEmpty()) {
            throw new EntityNotFoundException(String.format("%s 식별번호에 해당하는 회원이 존재하지 않습니다.", userIds));
        }
    }

    @Transactional(readOnly = true)
    public User findById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[%d] 식별번호에 해당하는 회원이 존재하지 않습니다.", userId)));
    }

    @Transactional(readOnly = true)
    public SelectAllUserInTeamResponse findAllUserInTeam(final Long teamId) {
        Team team = teamService.findById(teamId);
        return new SelectAllUserInTeamResponse(team.getUsers());
    }
}
