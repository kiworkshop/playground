package playground.mapper;

import org.springframework.jdbc.core.RowMapper;
import playground.domain.Category;
import playground.domain.Document;
import playground.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document> {

    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Document.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .category(Category.valueOf(rs.getString("category")))
                .contents(rs.getString("contents"))
                .drafter(User.builder()
                        .id(rs.getLong("drafter_id"))
                        .build())
                .build();

    }
}
