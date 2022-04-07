package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("records")
public class DeviceRecordsPostgres {

    @Id
    public Long persistenceId;

    public Long entryPersistenceId;

    public String content;

    public String label;
}
