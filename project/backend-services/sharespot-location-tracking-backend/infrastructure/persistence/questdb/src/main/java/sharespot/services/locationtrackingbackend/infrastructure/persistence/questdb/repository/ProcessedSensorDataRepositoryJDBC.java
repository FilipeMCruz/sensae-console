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
    @Query(value = "INSERT INTO sensor_gps_data VALUES ( :dataId, :deviceName, :deviceId, cast(:gpsData as geohash(12c)), :reportedAt, :ts);")
    void insert(@Param("dataId") String dataId, @Param("deviceName") String deviceName, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("reportedAt") Long reportedAt, @Param("ts") Timestamp ts);
    
    @Query(value = "SELECT * FROM sensor_gps_data WHERE ts > :startTime AND ts < :endtime AND deviceId = :deviceId")
    List<ProcessedSensorDataDAOImpl> queryByDeviceId(@Param("deviceId") String deviceId, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Query(value = "SELECT * FROM sensor_gps_data WHERE ts > :startTime AND ts < :endtime AND deviceName = :deviceName")
    List<ProcessedSensorDataDAOImpl> queryByDeviceName(@Param("deviceName") String deviceName, @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
