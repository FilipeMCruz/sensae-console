package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.irrigationZone.AreaBoundariesMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.irrigationZone.IrrigationZoneMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.irrigationZone.IrrigationZoneOwnerMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone.AreaBoundariesRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone.IrrigationZoneOwnerRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone.IrrigationZoneRepositoryPostgres;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class IrrigationZoneRepositoryImpl implements IrrigationZoneRepository {

    private final AreaBoundariesRepositoryPostgres areaRepository;
    private final IrrigationZoneRepositoryPostgres irrigationZoneRepositoryPostgres;

    private final IrrigationZoneOwnerRepositoryPostgres irrigationZoneOwnerRepositoryPostgres;

    public IrrigationZoneRepositoryImpl(AreaBoundariesRepositoryPostgres areaRepository,
                                        IrrigationZoneRepositoryPostgres irrigationZoneRepositoryPostgres,
                                        IrrigationZoneOwnerRepositoryPostgres irrigationZoneOwnerRepositoryPostgres) {
        this.areaRepository = areaRepository;
        this.irrigationZoneRepositoryPostgres = irrigationZoneRepositoryPostgres;
        this.irrigationZoneOwnerRepositoryPostgres = irrigationZoneOwnerRepositoryPostgres;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Stream<IrrigationZone> fetchMultiple(Stream<IrrigationZoneId> id) {
        var ids = id.map(i -> i.value().toString()).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return Stream.empty();
        }

        var ownerAreas = irrigationZoneOwnerRepositoryPostgres.findAllByAreaIdIn(ids)
                .collect(Collectors.groupingBy(o -> o.areaId));

        var boundaries = areaRepository.findAllByAreaIdIn(ids)
                .collect(Collectors.groupingBy(o -> o.areaId));

        var irrigationZones = irrigationZoneRepositoryPostgres.findAllByDeletedFalseAndAreaIdIn(ids)
                .map(dao -> IrrigationZoneMapper.daoToModel(dao,
                        AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId).stream()),
                        IrrigationZoneOwnerMapper.daoToModel(ownerAreas.get(dao.areaId).stream())))
                .toList();
        return irrigationZones.stream();
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Stream<IrrigationZone> fetchAll(Ownership owners) {
        if (owners.isSystem()) {
            var boundaries = StreamSupport.stream(areaRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(o -> o.areaId));

            var irrigationZones = StreamSupport.stream(irrigationZoneRepositoryPostgres.findAllByDeletedFalse()
                            .spliterator(), false)
                    .map(dao -> IrrigationZoneMapper.daoToModel(dao, AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId)
                            .stream()), IrrigationZoneOwnerMapper.daoToModel(Stream.empty())))
                    .toList();
            return irrigationZones.stream();
        } else {
            var ownerAreas = irrigationZoneOwnerRepositoryPostgres.findAllByDomainIdIn(owners.value()
                            .stream()
                            .map(DomainId::value)
                            .map(UUID::toString)
                            .collect(Collectors.toList()))
                    .collect(Collectors.groupingBy(o -> o.areaId));

            var ownerAreaIds = ownerAreas.keySet();

            var boundaries = StreamSupport.stream(areaRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(o -> o.areaId));

            var irrigationZones = StreamSupport.stream(irrigationZoneRepositoryPostgres.findAllByDeletedFalseAndAreaIdIn(ownerAreaIds)
                            .spliterator(), false)
                    .map(dao -> IrrigationZoneMapper.daoToModel(dao, AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId)
                            .stream()), IrrigationZoneOwnerMapper.daoToModel(ownerAreas.get(dao.areaId).stream())))
                    .toList();
            return irrigationZones.stream();
        }
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public IrrigationZone save(IrrigationZone irrigationZone) {
        var irrigationZonePostgres = IrrigationZoneMapper.modelToDao(irrigationZone);

        irrigationZoneRepositoryPostgres.findByDeletedFalseAndAreaId(irrigationZone.id().value().toString())
                .ifPresent(areaPostgres -> {
                    irrigationZonePostgres.persistenceId = areaPostgres.persistenceId;
                    areaRepository.deleteAllByAreaId(irrigationZone.id().value().toString());
                });

        irrigationZoneRepositoryPostgres.save(irrigationZonePostgres);

        var boundariesDao = AreaBoundariesMapper.modelToDao(irrigationZone.area(), irrigationZone.id());
        areaRepository.saveAll(boundariesDao.collect(Collectors.toList()));

        var ownersDao = IrrigationZoneOwnerMapper.modelToDao(irrigationZone.owners().value().stream(), irrigationZone.id());
        irrigationZoneOwnerRepositoryPostgres.saveAll(ownersDao.collect(Collectors.toList()));

        return irrigationZone;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public void delete(IrrigationZoneId id) {
        irrigationZoneRepositoryPostgres.findByAreaId(id.value().toString()).ifPresent(zonePostgres -> {
            zonePostgres.deleted = true;
            irrigationZoneRepositoryPostgres.save(zonePostgres);
        });
    }
}
