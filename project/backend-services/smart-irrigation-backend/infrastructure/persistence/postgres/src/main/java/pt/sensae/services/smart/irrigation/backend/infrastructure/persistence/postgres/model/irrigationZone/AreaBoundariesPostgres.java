package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("gardening_area_boundary")
public class AreaBoundariesPostgres {

    @Id
    public Long persistenceId;

    @Column("area_id")
    public String areaId;

    @Column("position")
    public Integer position;

    @Column("latitude")
    public String latitude;

    @Column("longitude")
    public String longitude;

    @Column("altitude")
    public String altitude;
}
