package pt.sensae.services.notification.management.backend.infrastructure.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationBasicQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.NotificationRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class NotificationRepositoryImplTest extends IntegrationTest {

    @Autowired
    NotificationRepositoryImpl repository;

    @AfterEach
    public void cleanUp() throws SQLException {
        performQuery("TRUNCATE notification");
    }

    @Test
    public void ensureDatabaseCanBeReached() {
        var single = Domains.single(DomainId.of(UUID.randomUUID()));
        var contentType = ContentType.of("any", "thing", NotificationLevel.CRITICAL);
        var query = NotificationBasicQuery.of(single, List.of(contentType));
        
        Assertions.assertDoesNotThrow(() -> repository.find(query));
    }
}
