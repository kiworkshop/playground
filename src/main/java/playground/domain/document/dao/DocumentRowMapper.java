package playground.domain.document.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import playground.domain.document.entity.ApprovalState;
import playground.domain.document.entity.Category;
import playground.domain.document.entity.Document;
import playground.domain.user.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DocumentRowMapper implements RowMapper<Document> {

    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Document.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .contents(rs.getString("contents"))
                .drafter(User.builder()
                        .id(rs.getLong("drafter_id"))
                        .build())
                .category(Category.valueOf(rs.getString("category")))
                .approvalState(ApprovalState.valueOf(rs.getString("approval_state")))
                .build();
    }
}
