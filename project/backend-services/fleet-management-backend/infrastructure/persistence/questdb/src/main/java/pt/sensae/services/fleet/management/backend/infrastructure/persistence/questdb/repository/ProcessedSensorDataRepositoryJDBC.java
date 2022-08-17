package pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;

import java.util.List;

@Repository
public interface ProcessedSensorDataRepositoryJDBC extends CrudRepository<ProcessedSensorDataDAOImpl, Long> {

    @Query(value = "SELECT * FROM data WHERE datediff('m', ts, :reportedAt) < :time_span AND device_id = :deviceId;")
    List<ProcessedSensorDataDAOImpl> latestDeviceDataInTime(@Param("deviceId") String deviceId, @Param("reportedAt") String reportedAt, @Param("time_span") Integer timeSpanMinutes);
}
