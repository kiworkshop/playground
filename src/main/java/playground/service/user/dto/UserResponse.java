package playground.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .insertDate(user.getInsertDate())
                .updateDate(user.getUpdateDate())
                .build();
    }
}
