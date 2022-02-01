package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.data.MotionDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationWithRecordsDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

@Service
public class ProcessedSensorDataMapperImpl {

    public ProcessedSensorDataDAOImpl dtoToDao(ProcessedSensorDataWithRecordsDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.deviceName = String.valueOf(in.device.name);
        dao.reportedAt = Timestamp.from(Instant.ofEpochMilli(in.reportedAt));
        dao.motion = toDAO(in);
        dao.gpsData = GeoHash.withCharacterPrecision(in.data.gps.latitude, in.data.gps.longitude, 12).toBase32();
        return dao;
    }

    private String toDAO(ProcessedSensorDataWithRecordsDTO in) {
        if (!in.data.hasProperty(PropertyName.MOTION)) {
            return "UNKNOWN";
        } else if ("ACTIVE".equalsIgnoreCase(in.data.motion.value)) {
            return "ACTIVE";
        } else if ("INACTIVE".equalsIgnoreCase(in.data.motion.value)) {
            return "INACTIVE";
        } else {
            return "UNKNOWN";
        }
    }

    public ProcessedSensorDataWithRecordsDTO daoToDto(ProcessedSensorDataDAOImpl dao) {
        var dataId = UUID.fromString(dao.dataId);
        var device = new DeviceInformationWithRecordsDTO(UUID.fromString(dao.deviceId), dao.deviceName, new DeviceRecordDTO(new HashSet<>()));
        var originatingPoint = GeoHash.fromGeohashString(dao.gpsData).getOriginatingPoint();
        var gpsDataDTO = GPSDataDTO.ofLatLong(originatingPoint.getLatitude(), originatingPoint.getLongitude());
        var statusDTO = MotionDataDTO.of(dao.motion);
        var details = new SensorDataDetailsDTO().withGps(gpsDataDTO).withMotion(statusDTO);
        return new ProcessedSensorDataWithRecordsDTO(dataId, device, dao.reportedAt.getTime(), details);
    }

    public ProcessedSensorDataDAOImpl toSensorData(ResultSet resultSet) throws SQLException {
        ProcessedSensorDataDAOImpl dataDAO = new ProcessedSensorDataDAOImpl();
        dataDAO.gpsData = resultSet.getString("gps_data");
        dataDAO.dataId = resultSet.getString("data_id");
        dataDAO.deviceId = resultSet.getString("device_id");
        dataDAO.deviceName = resultSet.getString("device_name");
        dataDAO.reportedAt = resultSet.getTimestamp("reported_at");
        dataDAO.motion = resultSet.getString("motion");
        dataDAO.ts = resultSet.getTimestamp("ts");
        return dataDAO;
    }
}
