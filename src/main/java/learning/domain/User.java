package learning.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class User {

    private final Long id;
    private final String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getId().equals(user.getId()) && getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
