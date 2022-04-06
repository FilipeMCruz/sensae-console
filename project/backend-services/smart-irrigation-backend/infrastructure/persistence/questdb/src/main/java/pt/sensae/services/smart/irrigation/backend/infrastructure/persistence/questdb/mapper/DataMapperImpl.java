package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class DataMapperImpl {

    public static Data toModel(DataQuestDB dao) {
        Payload payload;
        if (Objects.equals(dao.deviceType, "park")) {
            payload = new ParkPayload(Illuminance.of(dao.illuminance), SoilMoisture.of(dao.soilMoisture));
        } else if (Objects.equals(dao.deviceType, "stove")) {
            payload = new StovePayload(Temperature.of(dao.temperature), Humidity.of(dao.humidity));
        } else {
            throw new NotValidException("Data Type for " + dao.deviceType + " not supported");
        }
        var id = DataId.of(UUID.fromString(dao.dataId));
        var deviceId = DeviceId.of(UUID.fromString(dao.deviceType));
        var reportedAt = ReportTime.of(dao.reportedAt.toInstant().toEpochMilli());
        return new Data(id, deviceId, reportedAt, payload);
    }

    public static DataQuestDB toDao(Data model) {
        var dataQuestDB = new DataQuestDB();
        dataQuestDB.dataId = model.id().value().toString();
        dataQuestDB.deviceId = model.deviceId().value().toString();
        dataQuestDB.reportedAt = Timestamp.from(model.reportedAt().value().plusNanos(1));
        if (model.payload() instanceof ParkPayload parkPayload) {
            dataQuestDB.soilMoisture = parkPayload.soilMoisture().percentage();
            dataQuestDB.illuminance = parkPayload.soilMoisture().percentage();
            dataQuestDB.deviceType = "park";
        } else if (model.payload() instanceof StovePayload stovePayload) {
            dataQuestDB.humidity = stovePayload.humidity().gramsPerCubicMeter();
            dataQuestDB.temperature = stovePayload.temperature().celsius();
            dataQuestDB.deviceType = "stove";
        }
        return dataQuestDB;
    }
}
