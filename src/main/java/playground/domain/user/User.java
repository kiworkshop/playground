package playground.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;

    public User(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Builder(builderMethodName = "builderForDao")
    public User(final Long id, final String email, final String password, final String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

