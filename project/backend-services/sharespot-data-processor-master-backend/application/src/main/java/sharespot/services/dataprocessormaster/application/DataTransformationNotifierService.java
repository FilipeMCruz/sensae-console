package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationCollector;

@Service
public class DataTransformationNotifierService {

    private final DataTransformationEventMapper eventMapper;
    private final DataTransformationCollector collector;

    private final DataTransformationEventHandlerService handler;

    public DataTransformationNotifierService(DataTransformationEventMapper eventMapper,
                                             DataTransformationCollector collector,
                                             DataTransformationEventHandlerService handler) {
        this.eventMapper = eventMapper;
        this.collector = collector;
        this.handler = handler;
    }

    public void notify(SensorTypeIdDTO dto) {
        var type = eventMapper.dtoToDomain(dto);
        var collect = collector.collect(type);
        collect.ifPresent(handler::publishUpdate);
    }
}
