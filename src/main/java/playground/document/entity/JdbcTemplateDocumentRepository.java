package playground.document.entity;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
public class JdbcTemplateDocumentRepository implements DocumentRepository {

    private final DataSource dataSource;

    @Override
    public Long save(Document document) {
        return null;
    }

    @Override
    public Document findById(Long documentId) {
        return null;
    }

    @Override
    public List<Document> findAllByUserId(Long userId) {
        return null;
    }
}
