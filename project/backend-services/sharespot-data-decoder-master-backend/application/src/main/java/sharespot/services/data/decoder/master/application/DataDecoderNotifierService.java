package sharespot.services.data.decoder.master.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.domainservices.DataDecoderCollector;

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
