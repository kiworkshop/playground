package playground.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class JdbcConfig {

    private final DataSource dataSource;

    @Autowired
    public JdbcConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
