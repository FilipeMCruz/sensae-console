package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class DataMapperImpl {

    public static Data toModel(DataQuestDB dao) {
        Payload payload;
        if (Objects.equals(dao.deviceType, "park_sensor")) {
            payload = new ParkPayload(Illuminance.of(dao.illuminance), SoilMoisture.of(dao.soilMoisture));
        } else if (Objects.equals(dao.deviceType, "stove_sensor")) {
            payload = new StovePayload(Temperature.of(dao.temperature), Humidity.of(dao.humidity));
        } else if (Objects.equals(dao.deviceType, "valve")) {
            payload = new ValvePayload(new ValveStatus(dao.valveStatus ? ValveStatusType.OPEN : ValveStatusType.CLOSE));
        } else {
            throw new NotValidException("Data Type for " + dao.deviceType + " not supported");
        }
        var id = DataId.of(UUID.fromString(dao.dataId));
        var deviceId = DeviceId.of(UUID.fromString(dao.deviceId));
        var reportedAt = ReportTime.of(dao.reportedAt.toInstant().toEpochMilli());
        return new Data(id, deviceId, reportedAt, payload);
    }

    public static DataQuestDB toDao(Data model) {
        var dataQuestDB = new DataQuestDB();
        dataQuestDB.dataId = model.id().value().toString();
        dataQuestDB.deviceId = model.deviceId().value().toString();
        dataQuestDB.reportedAt = Timestamp.from(model.reportedAt().value().plus(1, ChronoUnit.MICROS));

        if (model.payload() instanceof ParkPayload parkPayload) {
            dataQuestDB.soilMoisture = parkPayload.soilMoisture().percentage();
            dataQuestDB.illuminance = parkPayload.illuminance().lux();
            dataQuestDB.deviceType = "park_sensor";
        } else if (model.payload() instanceof StovePayload stovePayload) {
            dataQuestDB.humidity = stovePayload.humidity().relativePercentage();
            dataQuestDB.temperature = stovePayload.temperature().celsius();
            dataQuestDB.deviceType = "stove_sensor";
        } else if (model.payload() instanceof ValvePayload valvePayload) {
            dataQuestDB.valveStatus = valvePayload.status().value().equals(ValveStatusType.OPEN);
            dataQuestDB.deviceType = "valve";
        }
        return dataQuestDB;
    }

    public static DataQuestDB toSensorData(ResultSet resultSet) throws SQLException {
        var dataQuestDB = new DataQuestDB();
        dataQuestDB.dataId = resultSet.getString("data_id");
        dataQuestDB.deviceId = resultSet.getString("device_id");
        dataQuestDB.reportedAt = resultSet.getTimestamp("reported_at");
        dataQuestDB.deviceType = resultSet.getString("device_type");

        if ("park_sensor".equals(dataQuestDB.deviceType)) {
            dataQuestDB.soilMoisture = resultSet.getFloat("payload_soil_moisture");
            dataQuestDB.illuminance = resultSet.getFloat("payload_illuminance");
        } else if ("stove_sensor".equals(dataQuestDB.deviceType)) {
            dataQuestDB.humidity = resultSet.getFloat("payload_humidity");
            dataQuestDB.temperature = resultSet.getFloat("payload_temperature");
        } else if ("valve".equals(dataQuestDB.deviceType)) {
            dataQuestDB.valveStatus = resultSet.getBoolean("payload_valve_status");
        }
        return dataQuestDB;
    }
}
