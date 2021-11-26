package playground;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class PlaygroundConfig {

    @ConfigurationProperties("spring.datasource.hikari")
    @Bean
    public DataSource dataSource() throws SQLException {
        Server.createTcpServer("-tcp",
            "-tcpPort",
            "9099",
            "-tcpAllowOthers",
            "-ifNotExists"
        ).start();
        return new HikariDataSource();
    }
}
