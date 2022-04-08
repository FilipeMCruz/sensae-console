package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;

import java.sql.Timestamp;
import java.util.stream.Stream;

@Repository
public interface DataRepositoryQuestDB extends CrudRepository<DataQuestDB, Long> {

    @Modifying
    @Query(value = "INSERT INTO smart_irrigation_data (data_id, device_id, device_type, reported_at, payload_temperature, payload_humidity, payload_soil_moisture, payload_illuminance) VALUES ( :dataId, :deviceId, :deviceType, :reportedAt, :temperature, :humidity, :soilMoisture, :illuminance);")
    void insert(@Param("dataId") String dataId,
                @Param("deviceId") String deviceId,
                @Param("deviceType") String deviceType,
                @Param("reportedAt") Timestamp reportedAt,
                @Param("temperature") Float temperature,
                @Param("humidity") Float humidity,
                @Param("soilMoisture") Float soilMoisture,
                @Param("illuminance") Float illuminance);


    @Query(value = "SELECT * FROM smart_irrigation_data WHERE device_id IN :deviceIds AND reported_at BETWEEN ':start' AND ':end'")
    Stream<DataQuestDB> fetch(@Param("deviceIds") String deviceIdArray, @Param("start") String start, @Param("end") String end);

    @Query(value = "SELECT * FROM smart_irrigation_data LATEST BY device_id WHERE device_id IN :deviceIds")
    Stream<DataQuestDB> fetchLatest(@Param("deviceIds") String deviceIdArray);
}
