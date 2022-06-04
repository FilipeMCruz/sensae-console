package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("notification")
public class NotificationPostgres {

    @Id
    public Long persistenceId;

    @Column("id")
    public String id;

    @Column("category")
    public String category;

    @Column("sub_category")
    public String subCategory;

    @Column("level")
    public String level;

    @Column("description")
    public String description;

    @Column("reported_at")
    public Instant reportedAt;

    @Column("data_ids")
    public String dataIds;

    @Column("device_ids")
    public String deviceIds;

    @Column("domains")
    public String domains;

    @Column("other")
    public String other;
}
