package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProcessedSensorDataRepositoryJDBC extends CrudRepository<ProcessedSensorDataDAOImpl, Long> {

    @Modifying
    @Query(value = "INSERT INTO data VALUES ( :dataId, :deviceName, :deviceId, :gpsData, :reportedAt, now());")
    void insert(@Param("dataId") String dataId, @Param("deviceName") String deviceName, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("reportedAt") Timestamp reportedAt);

    @Query(value = "SELECT * FROM data WHERE reported_at > :startTime AND reported_at < :endTime AND (device_name = :device OR device_id = :device)")
    List<ProcessedSensorDataDAOImpl> queryByDevice(@Param("device") String device, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT * FROM data WHERE datediff('m', reported_at, :reportedAt) < :time_span AND device_id = :deviceId;")
    List<ProcessedSensorDataDAOImpl> latestDeviceDataInTime(@Param("deviceId") String deviceId, @Param("reportedAt") Timestamp reportedAt, @Param("time_span") Integer timeSpanMinutes);
    
    @Query(value = "SELECT * FROM data LATEST BY device_id;")
    List<ProcessedSensorDataDAOImpl> latestDataOfEachDevice();
}
