package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.Owners;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone.IrrigationZoneOwnerPostgres;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IrrigationZoneOwnerMapper {
    public static Stream<IrrigationZoneOwnerPostgres> modelToDao(Stream<DomainId> domains, IrrigationZoneId areaId) {
        return domains.map(d -> {
            var owner = new IrrigationZoneOwnerPostgres();
            owner.areaId = areaId.value().toString();
            owner.domainId = d.value().toString();
            return owner;
        });
    }

    public static Owners daoToModel(Stream<IrrigationZoneOwnerPostgres> owners) {
        var collect = owners
                .map(dao -> DomainId.of(UUID.fromString(dao.domainId)))
                .collect(Collectors.toSet());
        return new Owners(collect);
    }
}
