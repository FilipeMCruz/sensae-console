package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceRecordEventDTO;
import sharespot.services.devicerecordsbackend.application.RecordEventMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.BasicRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model.*;

import java.util.stream.Collectors;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    private static final String ERASE_TYPE = "erase";
    private static final String INDEX_TYPE = "index";

    @Override
    public DeviceRecordEventDTO domainToDto(DeviceRecords domain) {
        var eventDTO = new DeviceRecordsIndexEventDTOImpl();
        var dto = new DeviceRecordDTOImpl();
        var deviceDTO = new DeviceDTOImpl();
        deviceDTO.id = domain.device().id().value().toString();
        deviceDTO.name = domain.device().name().value();
        dto.device = deviceDTO;
        dto.entries = domain.records().entries().stream().map(e -> {
            var entry = new RecordEntryDTOImpl();
            if (e instanceof BasicRecordEntry) {
                entry.type = RecordTypeDTOImpl.BASIC;
            } else {
                entry.type = RecordTypeDTOImpl.SENSOR_DATA;
            }
            entry.content = e.getContent();
            entry.label = e.getLabel();
            return entry;
        }).collect(Collectors.toSet());
        eventDTO.records = dto;
        eventDTO.eventType = INDEX_TYPE;
        return eventDTO;
    }

    @Override
    public DeviceRecordEventDTO domainToDto(DeviceId domain) {
        var eventDTO = new DeviceRecordsDeleteEventDTOImpl();
        var deviceDTO = new DeviceIdDTOImpl();
        deviceDTO.deviceId = domain.value();
        eventDTO.records = deviceDTO;
        eventDTO.eventType = ERASE_TYPE;
        return eventDTO;
    }
}
