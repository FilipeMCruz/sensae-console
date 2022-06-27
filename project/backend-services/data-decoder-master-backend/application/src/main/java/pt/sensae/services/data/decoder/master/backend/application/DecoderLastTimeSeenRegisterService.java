package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;

@Service
public class DecoderLastTimeSeenRegisterService {

    private final DataDecoderEventMapper eventMapper;

    private final LastTimeSeenDecoderRepository repository;

    public DecoderLastTimeSeenRegisterService(DataDecoderEventMapper eventMapper, LastTimeSeenDecoderRepository repository) {
        this.eventMapper = eventMapper;
        this.repository = repository;
    }

    public void updateLastTimeSeen(SensorTypeIdDTO dto) {
        var sensorTypeId = eventMapper.dtoToDomain(dto);
        this.repository.update(sensorTypeId);
    }
}
