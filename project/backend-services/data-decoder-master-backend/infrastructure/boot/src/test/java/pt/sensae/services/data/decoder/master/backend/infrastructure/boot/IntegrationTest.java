package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.sensae.services.data.decoder.master.backend.infrastructure.containers.DatabaseContainerTest;
import pt.sensae.services.data.decoder.master.backend.infrastructure.containers.MessageBrokerContainerTest;

import java.util.logging.Level;
import java.util.logging.LogManager;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {IntegrationTest.Initializer.class})
public abstract class IntegrationTest {
    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }
    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword(),
                    "spring.rabbitmq.host=" + rabbitMQContainer.getHost(),
                    "spring.rabbitmq.port=" + rabbitMQContainer.getAmqpPort(),
                    "spring.rabbitmq.username=" + rabbitMQContainer.getAdminUsername(),
                    "spring.rabbitmq.password=" + rabbitMQContainer.getAdminPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Container
    public static PostgreSQLContainer<?> postgresSQLContainer = DatabaseContainerTest.getInstance();

    @Container
    public static RabbitMQContainer rabbitMQContainer = MessageBrokerContainerTest.getInstance();
}
