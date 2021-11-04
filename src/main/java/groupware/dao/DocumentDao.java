package groupware.dao;

import groupware.domain.Category;
import groupware.domain.Document;
import groupware.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Component
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;

    public DocumentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Document findById(Long Id){
        String query = "select * from document where id = ?";
        List<Document> documents = jdbcTemplate.query(query, new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                Document document = Document.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .content(rs.getString("content"))
                        .drafter(User.builder()
                                .id(rs.getLong("drafter"))
                                .build())
                        .build();
                return document;
            }
        }, Id);
        return documents.get(0);
    }
}
