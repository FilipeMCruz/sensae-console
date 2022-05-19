package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea.AreaBoundariesMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea.GardeningAreaMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea.GardeningAreaOwnerMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaOwnerPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea.AreaBoundariesRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea.GardeningAreaOwnerRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea.GardeningAreaRepositoryPostgres;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class GardenRepositoryImpl implements GardenRepository {

    private final AreaBoundariesRepositoryPostgres areaRepository;
    private final GardeningAreaRepositoryPostgres gardeningAreaRepository;

    private final GardeningAreaOwnerRepositoryPostgres areaOwnerRepositoryPostgres;

    public GardenRepositoryImpl(AreaBoundariesRepositoryPostgres areaRepository,
                                GardeningAreaRepositoryPostgres gardeningAreaRepository,
                                GardeningAreaOwnerRepositoryPostgres areaOwnerRepositoryPostgres) {
        this.areaRepository = areaRepository;
        this.gardeningAreaRepository = gardeningAreaRepository;
        this.areaOwnerRepositoryPostgres = areaOwnerRepositoryPostgres;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Stream<GardeningArea> fetchMultiple(Stream<GardeningAreaId> id) {
        var ids = id.map(i -> i.value().toString()).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return Stream.empty();
        }

        var ownerAreas = areaOwnerRepositoryPostgres.findAllByAreaIdIn(ids)
                .collect(Collectors.groupingBy(o -> o.areaId));

        var boundaries = areaRepository.findAllByAreaIdIn(ids)
                .collect(Collectors.groupingBy(o -> o.areaId));

        var gardeningAreas = gardeningAreaRepository.findAllByDeletedFalseAndAreaIdIn(ids)
                .map(dao -> GardeningAreaMapper.daoToModel(dao,
                        AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId).stream()),
                        GardeningAreaOwnerMapper.daoToModel(ownerAreas.get(dao.areaId).stream())))
                .toList();
        return gardeningAreas.stream();
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Stream<GardeningArea> fetchAll(Stream<DomainId> owners) {
        var ownerAreas = areaOwnerRepositoryPostgres.findAllByDomainIdIn(owners.map(DomainId::value)
                        .map(UUID::toString)
                        .collect(Collectors.toList()))
                .collect(Collectors.groupingBy(o -> o.areaId));

        var ownerAreaIds = ownerAreas.keySet();

        var boundaries = StreamSupport.stream(areaRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(o -> o.areaId));

        var gardeningAreas = StreamSupport.stream(gardeningAreaRepository.findAllByDeletedFalseAndAreaIdIn(ownerAreaIds)
                        .spliterator(), false)
                .map(dao -> GardeningAreaMapper.daoToModel(dao, AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId)
                        .stream()), GardeningAreaOwnerMapper.daoToModel(ownerAreas.get(dao.areaId).stream())))
                .toList();
        return gardeningAreas.stream();
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public GardeningArea save(GardeningArea garden) {
        var gardeningAreaPostgres = GardeningAreaMapper.modelToDao(garden);

        gardeningAreaRepository.findByDeletedFalseAndAreaId(garden.id().value().toString())
                .ifPresent(areaPostgres -> {
                    gardeningAreaPostgres.persistenceId = areaPostgres.persistenceId;
                    areaRepository.deleteAllByAreaId(garden.id().value().toString());
                });

        gardeningAreaRepository.save(gardeningAreaPostgres);

        var boundariesDao = AreaBoundariesMapper.modelToDao(garden.area(), garden.id());
        areaRepository.saveAll(boundariesDao.collect(Collectors.toList()));

        var ownersDao = GardeningAreaOwnerMapper.modelToDao(garden.owners().value().stream(), garden.id());
        areaOwnerRepositoryPostgres.saveAll(ownersDao.collect(Collectors.toList()));

        return garden;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public void delete(GardeningAreaId id) {
        gardeningAreaRepository.findByAreaId(id.value().toString()).ifPresent(garden -> {
            garden.deleted = true;
            gardeningAreaRepository.save(garden);
        });
    }
}
