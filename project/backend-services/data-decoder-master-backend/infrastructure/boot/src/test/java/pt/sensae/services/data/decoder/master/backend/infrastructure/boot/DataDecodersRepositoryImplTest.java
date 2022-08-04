package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.DataDecodersRepositoryImpl;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.LogManager;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {DataDecodersRepositoryImplTest.Initializer.class})
public class DataDecodersRepositoryImplTest {
    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    DataDecodersRepositoryImpl repository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = DatabaseContainerTest.getInstance();

    @AfterEach
    public void cleanUp() throws SQLException {
        performQuery(postgreSQLContainer, "TRUNCATE decoder");
    }

    @Test
    public void ensureADecoderCanBeSaved() throws SQLException {
        var dataDecoder = new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("ascma"));
        var save = repository.save(dataDecoder);

        Assertions.assertEquals("lgt92", save.id().getValue());
        Assertions.assertEquals("ascma", save.script().value());

        var resultSet = performQuery(postgreSQLContainer, "SELECT * FROM decoder WHERE device_type LIKE 'lgt92' AND script LIKE 'ascma'");
        Assertions.assertEquals("lgt92", resultSet.getString("device_type"));
        Assertions.assertEquals("ascma", resultSet.getString("script"));
    }

    @Test
    public void ensureSavedDecoderCanBeFound() throws SQLException {
        performQuery(postgreSQLContainer, "INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma') RETURNING *");

        var found = repository.findById(SensorTypeId.of("lgt92")).orElseThrow();

        Assertions.assertEquals("lgt92", found.id().getValue());
        Assertions.assertEquals("ascma", found.script().value());
    }

    @Test
    public void ensureUnknownDecoderCantBeFound() {
        var found = repository.findById(SensorTypeId.of("lgt92"));

        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    public void ensureADecoderCanBeUpdated() throws SQLException {
        var resultSetSt = performQuery(postgreSQLContainer, "INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma') RETURNING *");
        Assertions.assertEquals("lgt92", resultSetSt.getString("device_type"));
        Assertions.assertEquals("ascma", resultSetSt.getString("script"));

        var dataDecoder = new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("nonono"));
        var save = repository.save(dataDecoder);

        Assertions.assertEquals("lgt92", save.id().getValue());
        Assertions.assertEquals("nonono", save.script().value());

        var resultSet = performQuery(postgreSQLContainer, "SELECT * FROM decoder WHERE device_type LIKE 'lgt92' AND script LIKE 'nonono'");
        Assertions.assertEquals("lgt92", resultSet.getString("device_type"));
        Assertions.assertEquals("nonono", resultSet.getString("script"));
    }

    @Test
    public void ensureSavedDecodersCanBeCollected() throws SQLException {
        performQuery(postgreSQLContainer, "INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma'),('emd300th', 'lololol') RETURNING *");

        var found = repository.findAll().toList();
        Assertions.assertEquals(2, found.size());

        Assertions.assertTrue(found.stream().anyMatch(s -> s.id().getValue().equals("lgt92")));
        Assertions.assertEquals("ascma", found.stream()
                .filter(s -> s.id().getValue().equals("lgt92"))
                .findFirst()
                .orElseThrow()
                .script()
                .value());

        Assertions.assertTrue(found.stream().anyMatch(s -> s.id().getValue().equals("emd300th")));
        Assertions.assertEquals("lololol", found.stream()
                .filter(s -> s.id().getValue().equals("emd300th"))
                .findFirst()
                .orElseThrow()
                .script()
                .value());
    }

    @Test
    public void ensureSavedDecoderCanBeDeleted() throws SQLException {
        performQuery(postgreSQLContainer, "INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma') RETURNING *");

        var found = repository.delete(SensorTypeId.of("lgt92"));

        Assertions.assertEquals("lgt92", found.getValue());

        var resultSet = performQuery(postgreSQLContainer, "SELECT * FROM decoder WHERE device_type LIKE 'lgt92'");
        Assertions.assertFalse(resultSet.next());
    }

    protected ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        if (resultSet != null)
            resultSet.next();

        return resultSet;
    }

    protected DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(container.getJdbcUrl());
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(config);
    }
}
