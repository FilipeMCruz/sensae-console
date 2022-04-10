package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("gardening_area")
public class GardeningAreaPostgres {

    @Id
    public Long persistenceId;

    @Column("id")
    public String areaId;

    @Column("name")
    public String areaName;

    @Column("type")
    public String areaType;

    @Column("irrigation_system")
    public String irrigationSystem;
}
