package sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.model.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.model.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.model.data.types.MotionDataDTO;
import pt.sharespot.iot.core.sensor.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.sensor.model.device.domains.DeviceDomainPermissionsDTO;
import pt.sharespot.iot.core.sensor.model.device.records.DeviceRecordDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ProcessedSensorDataMapperImpl {

    public ProcessedSensorDataDAOImpl dtoToSingleDao(ProcessedSensorDataDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.deviceName = String.valueOf(in.device.name);
        dao.reportedAt = Timestamp.from(Instant.ofEpochMilli(in.reportedAt));
        dao.motion = toDAO(in);
        dao.gpsData = GeoHash.withCharacterPrecision(in.getSensorData().gps.latitude, in.getSensorData().gps.longitude, 12).toBase32();
        return dao;
    }

    public List<ProcessedSensorDataDAOImpl> dtoToDao(ProcessedSensorDataDTO in) {
        var dao = dtoToSingleDao(in);
        return Stream.concat(in.device.domains.read.stream(), in.device.domains.readWrite.stream())
                .map(domain -> dao.cloneWithDomain(domain.toString()))
                .distinct()
                .toList();
    }

    private String toDAO(ProcessedSensorDataDTO in) {
        if (!in.getSensorData().hasProperty(PropertyName.MOTION)) {
            return "UNKNOWN";
        } else if ("ACTIVE".equalsIgnoreCase(in.getSensorData().motion.value)) {
            return "ACTIVE";
        } else if ("INACTIVE".equalsIgnoreCase(in.getSensorData().motion.value)) {
            return "INACTIVE";
        } else {
            return "UNKNOWN";
        }
    }

    public ProcessedSensorDataDTO daoToDto(ProcessedSensorDataDAOImpl dao) {
        var dataId = UUID.fromString(dao.dataId);
        var device = new DeviceInformationDTO();
        device.id = UUID.fromString(dao.deviceId);
        device.name = dao.deviceName;
        device.records = new DeviceRecordDTO(new HashSet<>());
        device.domains = new DeviceDomainPermissionsDTO();
        device.domains.read = Set.of(UUID.fromString(dao.domainId));
        var originatingPoint = GeoHash.fromGeohashString(dao.gpsData).getOriginatingPoint();
        var details = new SensorDataDetailsDTO()
                .withGps(GPSDataDTO.ofLatLong(originatingPoint.getLatitude(), originatingPoint.getLongitude()))
                .withMotion(MotionDataDTO.of(dao.motion));
        return new ProcessedSensorDataDTO(dataId, device, dao.reportedAt.getTime(), Map.of(0, details));
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
        dataDAO.domainId = resultSet.getString("domain");
        return dataDAO;
    }
}
