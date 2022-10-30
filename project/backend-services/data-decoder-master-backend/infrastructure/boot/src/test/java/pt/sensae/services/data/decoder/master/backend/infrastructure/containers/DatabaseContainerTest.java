package pt.sensae.services.data.decoder.master.backend.infrastructure.containers;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseContainerTest extends PostgreSQLContainer<DatabaseContainerTest> {
    private static final String IMAGE_VERSION = "data-decoder-database";
    private static DatabaseContainerTest container;

    private DatabaseContainerTest() {
        super(DockerImageName.parse(IMAGE_VERSION).asCompatibleSubstituteFor("postgres:14.5"));
    }

    public static DatabaseContainerTest getInstance() {
        if (container == null) {
            container = new DatabaseContainerTest()
                    .withUsername("user")
                    .withPassword("sa")
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
}
