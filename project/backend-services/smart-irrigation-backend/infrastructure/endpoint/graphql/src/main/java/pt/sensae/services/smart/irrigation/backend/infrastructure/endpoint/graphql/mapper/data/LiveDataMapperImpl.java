package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.LiveDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilter;
import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilterDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.LiveDataFilterDTOImpl;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LiveDataMapperImpl implements LiveDataMapper {
    @Override
    public LiveDataFilter dtoToModel(LiveDataFilterDTO dto) {
        var filter = (LiveDataFilterDTOImpl) dto;
        return new LiveDataFilter(filter.gardens.stream().map(UUID::fromString).map(GardeningAreaId::new).collect(Collectors.toSet()),
                filter.devices.stream().map(UUID::fromString).map(DeviceId::new).collect(Collectors.toSet()), filter.content);
    }
}
