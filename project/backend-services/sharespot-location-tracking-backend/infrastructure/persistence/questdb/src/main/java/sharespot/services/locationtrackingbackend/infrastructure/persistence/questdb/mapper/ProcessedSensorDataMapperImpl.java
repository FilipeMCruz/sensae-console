package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataMapperImpl {

    public ProcessedSensorDataDAOImpl dtoToDao(ProcessedSensorDataWithRecordsDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.deviceName = String.valueOf(in.device.name);
        dao.reportedAt = Timestamp.from(Instant.ofEpochSecond(in.reportedAt));
        dao.gpsData = GeoHash.withCharacterPrecision(in.data.gps.latitude, in.data.gps.longitude, 12).toBase32();
        return dao;
    }

    public GPSSensorDataHistory daoToModel(GPSSensorDataFilter filters, List<ProcessedSensorDataDAOImpl> dto) {
        var history = new GPSSensorDataHistory();
        history.deviceId = filters.device;
        history.deviceName = filters.device;
        history.startTime = filters.startTime.getTime();
        history.endTime = filters.endTime.getTime();
        if (dto.size() != 0) {
            history.deviceId = dto.get(0).deviceId;
            history.deviceName = dto.get(0).deviceName;
            history.data = dto.stream().map(data -> {
                var originatingPoint = GeoHash.fromGeohashString(data.gpsData).getOriginatingPoint();
                return new GPSDataDetails(originatingPoint.getLatitude(), originatingPoint.getLongitude());
            }).collect(Collectors.toList());
        }
        return history;
    }
}
