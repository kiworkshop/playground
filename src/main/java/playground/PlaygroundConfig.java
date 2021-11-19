package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import playground.document.entity.DocumentRepository;
import playground.document.entity.JdbcTemplateDocumentRepository;

import javax.sql.DataSource;

@Configuration
public class PlaygroundConfig {

    private final DataSource dataSource;

    @Autowired
    public PlaygroundConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DocumentRepository documentRepository() {
        return new JdbcTemplateDocumentRepository(dataSource);
    }
}
