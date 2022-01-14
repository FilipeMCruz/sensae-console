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
    @Query(value = "INSERT INTO location_tracking_data VALUES ( :dataId, :deviceName, :deviceId, cast(:gpsData as geohash(12c)), :reportedAt, now());")
    void insert(@Param("dataId") String dataId, @Param("deviceName") String deviceName, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("reportedAt") Timestamp reportedAt);

    @Query(value = "SELECT * FROM location_tracking_data WHERE reported_at > :startTime AND reported_at < :endTime AND (device_name = :device OR device_id = :device)")
    List<ProcessedSensorDataDAOImpl> queryByDevice(@Param("device") String device, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
