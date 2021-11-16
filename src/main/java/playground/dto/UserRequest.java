package playground.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;

    @Builder
    public UserRequest(String name) {
        this.name = name;
    }
}