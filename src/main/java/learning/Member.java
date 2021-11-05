package learning;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    private Long id;

    @Column(name = "name")
    private String userName;
    private Integer age;

    public Member(Long id, String userName, Integer age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public void update(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

}
