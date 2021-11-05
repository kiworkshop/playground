package learning;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaLearningTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("playground");
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @DisplayName("Member Create / Read / Delete 테스트")
    @Test
    void crd() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member(1L, "memberName", 20);
        em.persist(member);

        transaction.commit();

        EntityManager em2 = emf.createEntityManager();
        Member foundMember = em2.find(Member.class, 1L);
        assertThat(foundMember)
                .extracting("id", "userName", "age")
                .containsExactly(1L, "memberName", 20);

        remove(em2, foundMember);

        Member foundMember2 = em2.find(Member.class, 1L);
        assertThat(foundMember2).isNull();
    }

    @DisplayName("한번 영속화한 엔티티는 같은 인스턴스다.")
    @Test
    void find() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member(1L, "memberName", 20);
        em.persist(member);

        transaction.commit();

        Member foundMember = em.find(Member.class, 1L);
        assertThat(member == foundMember).isTrue();

        remove(em, foundMember);
    }

    @DisplayName("엔티티의 값을 수정하기만 하면 update 쿼리가 나간다.")
    @Test
    void update() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member(1L, "memberName", 20);
        em.persist(member);

        transaction.commit();

        Member foundMember = em.find(Member.class, 1L);
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();

        foundMember.update("updatedName", 30);

        transaction2.commit();

        Member foundMember2 = em.find(Member.class, 1L);
        assertThat(foundMember2)
                .extracting("id", "userName", "age")
                .containsExactly(1L, "updatedName", 30);

        remove(em, foundMember2);
    }

    private void remove(EntityManager em, Member foundMember) {
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();

        em.remove(foundMember);

        transaction2.commit();
    }

}
