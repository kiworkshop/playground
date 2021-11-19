package playground.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "ID")
    private Long id;

    private String email;
    private String password;
    private String name;

    @Builder
    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
