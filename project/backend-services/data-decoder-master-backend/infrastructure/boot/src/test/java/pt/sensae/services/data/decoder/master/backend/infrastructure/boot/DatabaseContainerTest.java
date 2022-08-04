package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class DatabaseContainerTest extends PostgreSQLContainer<DatabaseContainerTest> {
    private static final String IMAGE_VERSION = "project_data-decoder-database";
    private static DatabaseContainerTest container;
    
    private static HikariDataSource hikariConfig;

    private DatabaseContainerTest() {
        super(DockerImageName.parse(IMAGE_VERSION).asCompatibleSubstituteFor("postgres:14.3"));
    }

    public static DatabaseContainerTest getInstance() {
        if (container == null) {
            container = new DatabaseContainerTest()
                    .withDatabaseName("decoder")
                    .withUsername("user")
                    .withPassword("sa")
                    .withEnv("POSTGRESQL_DATABASE", "decoder")
                    .withEnv("POSTGRESQL_USER", "user")
                    .withEnv("POSTGRESQL_PASSWORD", "sa")
                    .withExposedPorts(PostgreSQLContainer.POSTGRESQL_PORT);
        }
        return container;
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

    protected DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        if(hikariConfig == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(container.getJdbcUrl());
            config.setUsername(container.getUsername());
            config.setPassword(container.getPassword());
            config.setDriverClassName(container.getDriverClassName());
            hikariConfig = new HikariDataSource(config);
        }
        return hikariConfig;
    }
}
