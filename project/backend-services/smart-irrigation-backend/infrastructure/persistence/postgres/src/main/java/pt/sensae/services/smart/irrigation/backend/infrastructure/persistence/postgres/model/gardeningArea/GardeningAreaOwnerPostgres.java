package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("gardening_area_owner")
public class GardeningAreaOwnerPostgres {

    @Id
    public Long persistenceId;

    @Column("area_id")
    public String areaId;

    @Column("domain_id")
    public String domainId;
}
