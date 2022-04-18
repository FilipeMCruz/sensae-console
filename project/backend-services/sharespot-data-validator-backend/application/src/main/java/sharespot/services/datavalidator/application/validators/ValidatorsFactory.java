package sharespot.services.datavalidator.application.validators;

import org.springframework.stereotype.Service;
import sharespot.services.datavalidator.application.validators.trigger.TriggerDataValidator;
import sharespot.services.datavalidator.application.validators.aqi.AQIDataValidator;
import sharespot.services.datavalidator.application.validators.battery.BatteryDataValidator;
import sharespot.services.datavalidator.application.validators.gps.GPSDataValidator;
import sharespot.services.datavalidator.application.validators.airHumidity.AirHumidityDataValidator;
import sharespot.services.datavalidator.application.validators.illuminance.IlluminanceDataValidator;
import sharespot.services.datavalidator.application.validators.soilMoisture.SoilMoistureDataValidator;
import sharespot.services.datavalidator.application.validators.motion.MotionDataValidator;
import sharespot.services.datavalidator.application.validators.airPressure.AirPressureDataValidator;
import sharespot.services.datavalidator.application.validators.temperature.TemperatureDataValidator;
import sharespot.services.datavalidator.application.validators.velocity.VelocityDataValidator;

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
                new VelocityDataValidator()
        );
    }
}
