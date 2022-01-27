package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.data.StatusDataDTO;
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

    private byte toDAO(ProcessedSensorDataWithRecordsDTO in) {
        if (!in.data.hasProperty(PropertyName.MOTION)) {
            return 0;
        } else if ("UNKNOWN".equals(in.data.status.motion)) {
            return 0;
        } else if ("ACTIVE".equalsIgnoreCase(in.data.status.motion)) {
            return 1;
        } else if ("INACTIVE".equalsIgnoreCase(in.data.status.motion)) {
            return 2;
        } else {
            return 0;
        }
    }

    private String fromDAO(ProcessedSensorDataDAOImpl dao) {
        if (dao.motion == null) {
            return "UNKNOWN";
        } else if (dao.motion == 0) {
            return "UNKNOWN";
        } else if (dao.motion == 1) {
            return "ACTIVE";
        } else if (dao.motion == 2) {
            return "INACTIVE";
        } else {
            return "UNKNOWN";
        }
    }

    public ProcessedSensorDataWithRecordsDTO daoToDto(ProcessedSensorDataDAOImpl dao) {
        var dataId = UUID.fromString(dao.dataId);
        var device = new DeviceInformationWithRecordsDTO(UUID.fromString(dao.deviceId), dao.deviceName, new DeviceRecordDTO(new HashSet<>()));
        var originatingPoint = GeoHash.fromGeohashString(dao.gpsData).getOriginatingPoint();
        var gpsDataDTO = new GPSDataDTO(originatingPoint.getLatitude(), originatingPoint.getLongitude());
        var statusDTO = StatusDataDTO.withMotion(fromDAO(dao));
        var details = new SensorDataDetailsDTO().withGps(gpsDataDTO).withStatus(statusDTO);
        return new ProcessedSensorDataWithRecordsDTO(dataId, device, dao.reportedAt.getTime(), details);
    }

    public ProcessedSensorDataDAOImpl toSensorData(ResultSet resultSet) throws SQLException {
        ProcessedSensorDataDAOImpl dataDAO = new ProcessedSensorDataDAOImpl();
        dataDAO.gpsData = resultSet.getString("gps_data");
        dataDAO.dataId = resultSet.getString("data_id");
        dataDAO.deviceId = resultSet.getString("device_id");
        dataDAO.deviceName = resultSet.getString("device_name");
        dataDAO.reportedAt = resultSet.getTimestamp("reported_at");
        dataDAO.motion = resultSet.getByte("motion");
        dataDAO.ts = resultSet.getTimestamp("ts");
        return dataDAO;
    }
}
