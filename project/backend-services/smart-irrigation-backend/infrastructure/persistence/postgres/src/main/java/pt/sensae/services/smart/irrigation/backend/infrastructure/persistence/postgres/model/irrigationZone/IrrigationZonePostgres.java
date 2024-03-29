package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("gardening_area")
public class IrrigationZonePostgres {

    @Id
    public Long persistenceId;

    @Column("id")
    public String areaId;

    @Column("name")
    public String areaName;

    @Column("hidden")
    public boolean deleted;
}
