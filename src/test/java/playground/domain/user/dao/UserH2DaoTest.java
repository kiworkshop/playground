package playground.domain.user.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.document.entity.Document;
import playground.domain.user.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserH2DaoTest {

    @Autowired
    private UserH2Dao userH2Dao;

    @Test
    @DisplayName("문서가 주어지면 작성자 유저를 찾아온다.")
    void find_drafter_of_success() {
        // given
        User expected = User.builder()
                .id(1L)
                .build();
        Document document = Document.builder()
                .id(1L)
                .drafter(expected)
                .build();

        // when
        User result = userH2Dao.findDrafterOf(document);

        // then
        assertThat(result).isEqualTo(expected);
    }
}