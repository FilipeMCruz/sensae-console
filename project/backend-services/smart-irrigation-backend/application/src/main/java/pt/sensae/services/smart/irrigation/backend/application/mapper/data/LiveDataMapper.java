package pt.sensae.services.smart.irrigation.backend.application.mapper.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilter;
import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilterDTO;

public interface LiveDataMapper {

    LiveDataFilter dtoToModel(LiveDataFilterDTO dto);
}
