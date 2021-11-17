package playground.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.BaseTimeEntity;
import playground.domain.document.Document;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @OneToMany(mappedBy = "drafter")
    private List<Document> documents;

    public User(String name) {
        this.name = name;
    }
}
