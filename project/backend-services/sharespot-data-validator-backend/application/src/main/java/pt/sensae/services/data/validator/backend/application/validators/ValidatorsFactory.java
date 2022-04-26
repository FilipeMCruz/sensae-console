package pt.sensae.services.data.validator.backend.application.validators;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.validator.backend.application.validators.airPressure.AirPressureDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.battery.BatteryDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.co.CODataValidator;
import pt.sensae.services.data.validator.backend.application.validators.co2.CO2DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.distance.DistanceDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.gps.GPSDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.motion.MotionDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.nh3.NH3DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.no2.NO2DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.o3.O3DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.occupation.OccupationDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.ph.PHDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.pm10.PM10DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.pm25.PM2_5DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.soilConductivity.SoilConductivityDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.soilMoisture.SoilMoistureDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.temperature.TemperatureDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.trigger.TriggerDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.velocity.VelocityDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.aqi.AQIDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.airHumidity.AirHumidityDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.illuminance.IlluminanceDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.voc.VOCDataValidator;
import pt.sensae.services.data.validator.backend.application.validators.waterPressure.WaterPressureDataValidator;

import java.util.List;

@Service
public class ValidatorsFactory {

    public static List<DataValidator> buildValidators() {
        return List.of(
                new TriggerDataValidator(),
                new AQIDataValidator(),
                new BatteryDataValidator(),
                new GPSDataValidator(),
                new AirHumidityDataValidator(),
                new IlluminanceDataValidator(),
                new SoilMoistureDataValidator(),
                new MotionDataValidator(),
                new AirPressureDataValidator(),
                new TemperatureDataValidator(),
                new VelocityDataValidator(),
                new WaterPressureDataValidator(),
                new VOCDataValidator(),
                new SoilConductivityDataValidator(),
                new PM2_5DataValidator(),
                new PM10DataValidator(),
                new PHDataValidator(),
                new OccupationDataValidator(),
                new O3DataValidator(),
                new NO2DataValidator(),
                new NH3DataValidator(),
                new DistanceDataValidator(),
                new CO2DataValidator(),
                new CODataValidator()
        );
    }
}
