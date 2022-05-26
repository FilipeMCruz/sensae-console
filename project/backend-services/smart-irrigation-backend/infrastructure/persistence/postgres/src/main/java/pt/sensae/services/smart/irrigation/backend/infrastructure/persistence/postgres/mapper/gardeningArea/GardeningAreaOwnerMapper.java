package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Owners;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaOwnerPostgres;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GardeningAreaOwnerMapper {
    public static Stream<GardeningAreaOwnerPostgres> modelToDao(Stream<DomainId> domains, GardeningAreaId areaId) {
        return domains.map(d -> {
            var owner = new GardeningAreaOwnerPostgres();
            owner.areaId = areaId.value().toString();
            owner.domainId = d.value().toString();
            return owner;
        });
    }

    public static Owners daoToModel(Stream<GardeningAreaOwnerPostgres> owners) {
        var collect = owners
                .map(dao -> DomainId.of(UUID.fromString(dao.domainId)))
                .collect(Collectors.toSet());
        return new Owners(collect);
    }
}
