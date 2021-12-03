package playground.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.domain.document.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@RequiredArgsConstructor(access = PROTECTED)
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "team", cascade = ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
