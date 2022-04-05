package pt.sensae.services.smart.irrigation.backend.domainservices.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Payload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.Illuminance;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.SoilMoisture;
import pt.sensae.services.smart.irrigation.backend.domain.model.device.DeviceId;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public class DataMapper {

    public static Data dtoToModel(ProcessedSensorDataDTO dto) {
        var id = DataId.of(dto.dataId);
        var deviceId = DeviceId.of(dto.dataId);
        var reportedAt = ReportTime.of(dto.reportedAt);
        var lux = Illuminance.of(dto.data.illuminance.lux.floatValue());
        var moisture = SoilMoisture.of(dto.data.moisture.percentage.floatValue());
        var payload = new Payload(lux, moisture);
        return new Data(id, deviceId, reportedAt, payload);
    }
}
