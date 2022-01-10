package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataMapperImpl {

    public ProcessedSensorDataDAOImpl dtoToDao(ProcessedSensorDataWithRecordsDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.deviceName = String.valueOf(in.device.name);
        dao.reportedAt = in.reportedAt;
        dao.ts = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MICROS));
        dao.gpsData = GeoHash.withCharacterPrecision(in.data.gps.latitude, in.data.gps.longitude, 12).toBase32();
        return dao;
    }

    public GPSSensorDataHistory daoToModel(GPSSensorDataQuery filters, List<ProcessedSensorDataDAOImpl> dto) {
        var history = new GPSSensorDataHistory();
        history.deviceId = filters.deviceId;
        history.deviceName = filters.deviceName;
        history.startTime = filters.startTime;
        history.endTime = filters.endTime;
        if (dto.size() != 0) {
            history.data = dto.stream().map(data -> {
                var originatingPoint = GeoHash.fromBinaryString(data.gpsData).getOriginatingPoint();
                return new GPSDataDetails(originatingPoint.getLatitude(), originatingPoint.getLongitude());
            }).collect(Collectors.toList());
        }
        return history;
    }
}
