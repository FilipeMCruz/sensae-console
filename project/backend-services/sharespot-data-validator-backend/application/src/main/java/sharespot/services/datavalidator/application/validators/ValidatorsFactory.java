package sharespot.services.datavalidator.application.validators;

import org.springframework.stereotype.Service;
import sharespot.services.datavalidator.application.validators.alarm.AlarmDataValidator;
import sharespot.services.datavalidator.application.validators.aqi.AQIDataValidator;
import sharespot.services.datavalidator.application.validators.battery.BatteryDataValidator;
import sharespot.services.datavalidator.application.validators.gps.GPSDataValidator;
import sharespot.services.datavalidator.application.validators.humidity.HumidityDataValidator;
import sharespot.services.datavalidator.application.validators.illuminance.IlluminanceDataValidator;
import sharespot.services.datavalidator.application.validators.moisture.MoistureDataValidator;
import sharespot.services.datavalidator.application.validators.motion.MotionDataValidator;
import sharespot.services.datavalidator.application.validators.pressure.PressureDataValidator;
import sharespot.services.datavalidator.application.validators.temperature.TemperatureDataValidator;
import sharespot.services.datavalidator.application.validators.velocity.VelocityDataValidator;

import java.util.List;

@Service
public class ValidatorsFactory {

    public static List<DataValidator> buildValidators() {
        return List.of(
                new AlarmDataValidator(),
                new AQIDataValidator(),
                new BatteryDataValidator(),
                new GPSDataValidator(),
                new HumidityDataValidator(),
                new IlluminanceDataValidator(),
                new MoistureDataValidator(),
                new MotionDataValidator(),
                new PressureDataValidator(),
                new TemperatureDataValidator(),
                new VelocityDataValidator()
        );
    }
}
