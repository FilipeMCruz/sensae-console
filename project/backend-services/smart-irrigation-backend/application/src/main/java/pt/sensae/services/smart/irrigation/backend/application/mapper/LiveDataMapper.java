package pt.sensae.services.smart.irrigation.backend.application.mapper;

import pt.sensae.services.smart.irrigation.backend.application.model.LiveDataFilter;
import pt.sensae.services.smart.irrigation.backend.application.model.LiveDataFilterDTO;

public interface LiveDataMapper {

    LiveDataFilter dtoToModel(LiveDataFilterDTO dto);
}
