package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea.AreaBoundariesMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea.GardeningAreaMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea.AreaBoundariesRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea.GardeningAreaRepositoryPostgres;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class GardenRepositoryImpl implements GardenRepository {

    private final AreaBoundariesRepositoryPostgres areaRepository;
    private final GardeningAreaRepositoryPostgres gardeningAreaRepository;

    public GardenRepositoryImpl(AreaBoundariesRepositoryPostgres areaRepository, GardeningAreaRepositoryPostgres gardeningAreaRepository) {
        this.areaRepository = areaRepository;
        this.gardeningAreaRepository = gardeningAreaRepository;
    }

    @Override
    public Optional<GardeningArea> fetchById(GardeningAreaId id) {
        var boundariesDao = areaRepository.findAllByAreaId(id.value().toString());

        return gardeningAreaRepository.findByAreaId(id.value().toString())
                .map(dao -> GardeningAreaMapper.daoToModel(dao, AreaBoundariesMapper.daoToModel(boundariesDao)));
    }

    @Override
    public Stream<GardeningArea> fetchMultiple(Stream<GardeningAreaId> id) {
        var ids = id.map(i -> i.value().toString()).toList();
        if (ids.isEmpty()) {
            return Stream.empty();
        }
        var boundaries = areaRepository.findAllByAreaIdIn(ids)
                .collect(Collectors.groupingBy(o -> o.areaId));

        return gardeningAreaRepository.findByAreaIdIn(ids)
                .map(dao -> GardeningAreaMapper.daoToModel(dao, AreaBoundariesMapper.daoToModel(boundaries.get(dao.areaId).stream())));
    }

    @Override
    public GardeningArea save(GardeningArea garden) {
        var gardeningAreaPostgres = GardeningAreaMapper.modelToDao(garden);

        gardeningAreaRepository.findByAreaId(garden.id().value().toString())
                .ifPresent(areaPostgres -> gardeningAreaPostgres.persistenceId = areaPostgres.persistenceId);

        gardeningAreaRepository.save(gardeningAreaPostgres);

        var boundariesDao = AreaBoundariesMapper.modelToDao(garden.area(), garden.id());
        areaRepository.saveAll(boundariesDao.collect(Collectors.toList()));

        return garden;
    }
}
