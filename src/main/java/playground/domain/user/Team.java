package playground.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@RequiredArgsConstructor(access = PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = ALL, orphanRemoval = true)
    private List<User> users;

    public Team(String name) {
        this.name = name;
    }
}
