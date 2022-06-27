package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("read")
public class ReadNotificationPostgres {

    @Id
    public Long persistenceId;

    @Column("id")
    public String id;

    @Column("tenant")
    public String tenant;

    @Column("reported_at")
    public Instant reportedAt;
}
