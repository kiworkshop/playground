package playground.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import playground.domain.Document;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class DocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DocumentRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(final Document document) {
        String saveQuery = "insert into document (title, category, contents, drafter_id, approval_state) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, document.getTitle());
            ps.setString(2, document.getCategory().name());
            ps.setString(3, document.getContents());
            ps.setLong(4, document.getDrafterId());
            ps.setString(5, document.getApprovalState().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
