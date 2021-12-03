package playground.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.BaseTimeEntity;
import playground.domain.document.Document;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @ManyToOne(fetch = LAZY)
    private Team team;

    @Enumerated(value = STRING)
    private JobPosition jobPosition;

    @OneToMany(mappedBy = "drafter")
    private List<Document> documents;

    @Builder
    public User(String name, String password, String email, Team team, JobPosition jobPosition) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.team = team;
        this.jobPosition = jobPosition;
    }
}
