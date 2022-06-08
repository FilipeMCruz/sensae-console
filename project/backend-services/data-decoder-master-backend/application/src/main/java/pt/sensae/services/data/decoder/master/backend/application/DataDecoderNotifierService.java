package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderCollector;

@Service
public class DataDecoderNotifierService {

    private final DataDecoderEventMapper eventMapper;
    private final DataDecoderCollector collector;

    private final DataDecoderHandlerService handler;

    public DataDecoderNotifierService(DataDecoderEventMapper eventMapper,
                                      DataDecoderCollector collector,
                                      DataDecoderHandlerService handler) {
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
