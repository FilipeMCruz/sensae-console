package pt.sensae.services.data.processor.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.domain.LastTimeSeenTransformationRepository;

@Service
public class TransformationLastTimeSeenRegisterService {

    private final DataTransformationEventMapper eventMapper;

    private final LastTimeSeenTransformationRepository repository;

    public TransformationLastTimeSeenRegisterService(DataTransformationEventMapper eventMapper, LastTimeSeenTransformationRepository repository) {
        this.eventMapper = eventMapper;
        this.repository = repository;
    }

    public void updateLastTimeSeen(SensorTypeIdDTO dto) {
        var sensorTypeId = eventMapper.dtoToDomain(dto);
        this.repository.update(sensorTypeId);
    }
}
