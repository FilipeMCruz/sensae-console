package sharespot.services.fastdatastore.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.fastdatastore.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.sql.Timestamp;

@Repository
public interface ProcessedSensorDataRepositoryJDBC extends CrudRepository<ProcessedSensorDataDAOImpl, Long> {

    @Modifying
    @Query(value = "INSERT INTO sensor_gps_data VALUES ( :dataId, :deviceId, cast(:gpsData as geohash(12c)), :reportedAt, :ts);")
    void insert(@Param("dataId") String dataId, @Param("deviceId") String deviceId, @Param("gpsData") String gpsData, @Param("reportedAt") Long reportedAt, @Param("ts") Timestamp ts);

}
