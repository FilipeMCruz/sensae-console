package sharespot.services.fastdatastore.infrastructure.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fastdatastore.application.ProcessedSensorDataDAO;
import sharespot.services.fastdatastore.application.ProcessedSensorDataMapper;
import sharespot.services.fastdatastore.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ProcessedSensorDataMapperImpl implements ProcessedSensorDataMapper {

    @Override
    public ProcessedSensorDataDAO dtoToDao(ProcessedSensorDataDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.reportedAt = in.reportedAt;
        dao.ts = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MICROS));
        dao.gpsData = GeoHash.withCharacterPrecision(in.data.gps.latitude, in.data.gps.longitude, 12).toBase32();

        return dao;
    }
}
