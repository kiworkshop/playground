package playground.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.domain.Team;
import playground.domain.User;
import playground.dto.UserResponse;
import playground.repository.TeamRepositiory;
import playground.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepositiory teamRepository;

    public List<UserResponse> findBy(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        List<User> users = userRepository.findByTeam(team);
        List<UserResponse> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(new UserResponse(user));
        }
        return usersResponse;
    }
}
