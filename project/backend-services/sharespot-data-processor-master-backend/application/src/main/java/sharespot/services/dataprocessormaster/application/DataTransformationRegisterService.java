package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationHoarder;

@Service
public class DataTransformationRegisterService {

    private final DataTransformationHoarder hoarder;

    private final DataTransformationMapper mapper;

    private final DataTransformationHandlerService publisher;

    public DataTransformationRegisterService(DataTransformationHoarder hoarder, DataTransformationMapper mapper, DataTransformationHandlerService publisher) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public DataTransformationDTO register(DataTransformationDTO dto) {
        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
        publisher.publishUpdate(deviceRecords.getId());
        return dto;
    }
}
