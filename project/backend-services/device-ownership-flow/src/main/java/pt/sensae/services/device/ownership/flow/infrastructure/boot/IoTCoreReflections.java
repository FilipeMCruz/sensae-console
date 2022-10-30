package pt.sensae.services.device.ownership.flow.infrastructure.boot;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.model.AlertLevel;
import pt.sharespot.iot.core.alert.model.CorrelationDataDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertCategoryOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.alert.routing.keys.AlertSeverityOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertSubCategoryOptions;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.data.DataUnitReadingsDTO;
import pt.sharespot.iot.core.data.model.data.types.*;
import pt.sharespot.iot.core.data.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.data.model.device.controls.DeviceCommandDTO;
import pt.sharespot.iot.core.data.model.device.records.DeviceRecordEntryDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.keys.*;

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
        PropertyName.class,
        AlertDTO.class,
        CorrelationDataDTO.class,
        AlertLevel.class,
        MessageConsumed.class,
        MessageSupplied.class,
        AlertRoutingKeys.class,
        RoutingKeyOption.class,
        ContainerTypeOptions.class,
        ProtocolVersionOptions.class,
        AlertSeverityOptions.class,
        AlertCategoryOptions.class,
        AlertSubCategoryOptions.class,
        OwnershipOptions.class
})
public class IoTCoreReflections {
}
