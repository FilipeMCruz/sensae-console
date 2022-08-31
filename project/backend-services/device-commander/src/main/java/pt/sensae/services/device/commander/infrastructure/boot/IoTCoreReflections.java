package pt.sensae.services.device.commander.infrastructure.boot;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.commander.application.model.DeviceCommandDTO;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.data.DataUnitReadingsDTO;
import pt.sharespot.iot.core.data.model.data.types.*;
import pt.sharespot.iot.core.data.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.data.model.device.records.DeviceRecordEntryDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;

@RegisterForReflection(targets = {
        DataUnitDTO.class,
        DeviceInformationDTO.class,
        DeviceRecordEntryDTO.class,
        DeviceCommandDTO.class,
        DataUnitReadingsDTO.class,
        GPSDataDTO.class,
        TemperatureDataDTO.class,
        MotionDataDTO.class,
        AirQualityDataDTO.class,
        VelocityDataDTO.class,
        AirHumidityDataDTO.class,
        AirPressureDataDTO.class,
        BatteryDataDTO.class,
        SoilMoistureDataDTO.class,
        IlluminanceDataDTO.class,
        TriggerDataDTO.class,
        WaterPressureDataDTO.class,
        DistanceDataDTO.class,
        PHDataDTO.class,
        OccupationDataDTO.class,
        SoilConductivityDataDTO.class,
        CO2DataDTO.class,
        CODataDTO.class,
        NH3DataDTO.class,
        NO2DataDTO.class,
        O3DataDTO.class,
        VOCDataDTO.class,
        PM2_5DataDTO.class,
        PM10DataDTO.class,
        PropertyName.class
})
public class IoTCoreReflections {
}
