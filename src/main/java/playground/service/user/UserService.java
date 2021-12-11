package playground.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.user.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findUsersByTeam(Long teamId) {
        List<User> users = userRepository.findByTeamId(teamId);

        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

}
