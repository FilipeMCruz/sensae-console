package pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.mapper;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;
import pt.sharespot.iot.core.data.model.data.DataUnitReadingsDTO;
import pt.sharespot.iot.core.data.model.data.types.GPSDataDTO;
import pt.sharespot.iot.core.data.model.data.types.MotionDataDTO;
import pt.sharespot.iot.core.data.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class ProcessedSensorDataMapperImpl {

    public ProcessedSensorDataDAOImpl dtoToSingleDao(DataUnitDTO in) {
        var dao = new ProcessedSensorDataDAOImpl();

        dao.dataId = in.dataId.toString();
        dao.deviceId = String.valueOf(in.device.id);
        dao.deviceName = String.valueOf(in.device.name);
        dao.reportedAt = Timestamp.from(Instant.ofEpochMilli(in.reportedAt));
        dao.motion = toDAO(in);
        dao.gpsData = GeoHash.withCharacterPrecision(in.getSensorData().gps.latitude, in.getSensorData().gps.longitude, 12)
                .toBase32();
        return dao;
    }

    public List<ProcessedSensorDataDAOImpl> dtoToDao(DataUnitDTO in) {
        var dao = dtoToSingleDao(in);
        return in.device.domains.stream()
                .map(domain -> dao.cloneWithDomain(domain.toString()))
                .distinct()
                .toList();
    }

    private String toDAO(DataUnitDTO in) {
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

    public DataUnitDTO daoToDto(ProcessedSensorDataDAOImpl dao) {
        var dataId = UUID.fromString(dao.dataId);
        var device = new DeviceInformationDTO();
        device.id = UUID.fromString(dao.deviceId);
        device.name = dao.deviceName;
        device.records = new HashSet<>();
        device.domains = Set.of(UUID.fromString(dao.domainId));
        var originatingPoint = GeoHash.fromGeohashString(dao.gpsData).getOriginatingPoint();
        var details = new DataUnitReadingsDTO()
                .withGps(GPSDataDTO.ofLatLong(originatingPoint.getLatitude(), originatingPoint.getLongitude()))
                .withMotion(MotionDataDTO.of(dao.motion));
        return new DataUnitDTO(dataId, device, dao.reportedAt.getTime(), Map.of(0, details));
    }

    public ProcessedSensorDataDAOImpl toSensorData(ResultSet resultSet) throws SQLException {
        ProcessedSensorDataDAOImpl dataDAO = new ProcessedSensorDataDAOImpl();
        dataDAO.gpsData = resultSet.getString("gps_data");
        dataDAO.dataId = resultSet.getString("data_id");
        dataDAO.deviceId = resultSet.getString("device_id");
        dataDAO.deviceName = resultSet.getString("device_name");
        dataDAO.reportedAt = resultSet.getTimestamp("ts");
        dataDAO.motion = resultSet.getString("motion");
        dataDAO.ts = resultSet.getTimestamp("ts");
        dataDAO.domainId = resultSet.getString("domain");
        return dataDAO;
    }
}
