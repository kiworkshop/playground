package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.*;
import playground.exception.NotFoundException;
import playground.service.user.dto.TeamUserResponse;
import playground.service.user.dto.UserRequest;
import playground.service.user.dto.UserResponse;

import java.util.List;

import static playground.exception.NotFoundException.error;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public UserResponse saveUser(UserRequest request) {
        Team team = teamRepository.findTeamByName(request.getTeamName());
        User user = userRepository.save(request.toUser(team, JobPosition.valueOf(request.getJobPosition())));
        return UserResponse.of(user);
    }

    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> error("사용자를 찾을 수 없습니다."));
        return UserResponse.of(user);
    }

    public List<TeamUserResponse> findUserByTeam(long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> error("팀을 찾을 수 없습니다."));

        List<User> users = userRepository.findUserByTeam(team);
        return TeamUserResponse.ofList(users, team);
    }
}
