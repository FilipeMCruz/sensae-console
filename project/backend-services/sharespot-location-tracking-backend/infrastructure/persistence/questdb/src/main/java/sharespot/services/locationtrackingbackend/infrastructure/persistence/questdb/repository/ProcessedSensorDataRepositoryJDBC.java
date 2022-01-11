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
    @Query(value = "INSERT INTO location_tracking_data VALUES ( :dataId, :deviceName, :deviceId, cast(:gpsData as geohash(12c)), :reportedAt, :ts);")
    void insert(@Param("dataId") String dataId, @Param("deviceName") String deviceName, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("reportedAt") Long reportedAt, @Param("ts") Timestamp ts);

    @Query(value = "SELECT * FROM location_tracking_data WHERE ts > :startTime AND ts < :endtime AND (deviceName = :device OR deviceId = : device)")
    List<ProcessedSensorDataDAOImpl> queryByDevice(@Param("device") String device, @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
