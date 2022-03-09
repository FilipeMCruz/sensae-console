package sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProcessedSensorDataRepositoryJDBC extends CrudRepository<ProcessedSensorDataDAOImpl, Long> {

    @Modifying
    @Query(value = "INSERT INTO data (data_id, device_name, device_id, gps_data, motion, reported_at, domain_id, ts) VALUES ( :dataId, :deviceName, :deviceId, :gpsData, :motion, :reportedAt, :domainId, now());")
    void insert(@Param("dataId") String dataId, @Param("deviceName") String deviceName, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("motion") String motion, @Param("reportedAt") Timestamp reportedAt, @Param("domainId") String domainId);

    @Query(value = "SELECT * FROM data WHERE datediff('m', ts, :reportedAt) < :time_span AND device_id = :deviceId;")
    List<ProcessedSensorDataDAOImpl> latestDeviceDataInTime(@Param("deviceId") String deviceId, @Param("reportedAt") String reportedAt, @Param("time_span") Integer timeSpanMinutes);
}
