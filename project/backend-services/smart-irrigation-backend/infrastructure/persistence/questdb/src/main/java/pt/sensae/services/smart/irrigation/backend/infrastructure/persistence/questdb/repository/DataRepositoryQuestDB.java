package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;

import java.sql.Timestamp;

@Repository
public interface DataRepositoryQuestDB extends CrudRepository<DataQuestDB, Long> {

    @Modifying
    @Query(value = "INSERT INTO smart_irrigation_data (data_id, device_id, device_type, reported_at, payload_temperature, payload_humidity, payload_soil_moisture, payload_illuminance, payload_valve_status) VALUES ( :dataId, :deviceId, :deviceType, :reportedAt, :temperature, :humidity, :soilMoisture, :illuminance, :valveStatus);")
    void insert(@Param("dataId") String dataId,
                @Param("deviceId") String deviceId,
                @Param("deviceType") String deviceType,
                @Param("reportedAt") Timestamp reportedAt,
                @Param("temperature") Float temperature,
                @Param("humidity") Float humidity,
                @Param("soilMoisture") Float soilMoisture,
                @Param("illuminance") Float illuminance,
                @Param("valveStatus") Boolean valveStatus);
}
