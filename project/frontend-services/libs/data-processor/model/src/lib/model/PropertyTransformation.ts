import {PropertyName} from './PropertyName';

export class PropertyTransformation {
  constructor(public oldPath: string, public newPath: PropertyName, public subSensorId: number) {
  }

  static empty() {
    return new PropertyTransformation('', PropertyName.INVALID, 0);
  }

  clone() {
    return new PropertyTransformation(this.oldPath, this.newPath, this.subSensorId);
  }

  isValid() {
    return (
      this.newPath !== PropertyName.INVALID && this.oldPath.trim().length !== 0
    );
  }

  getSubSensor() {
    return this.subSensorId === 0 ? "Controller" : "Sensor " + this.subSensorId;
  }

  getIcon(): string {
    switch (this.newPath) {
      case PropertyName.DATA_ID:
        return 'fingerprint';
      case PropertyName.DEVICE_ID:
        return 'sensors';
      case PropertyName.DEVICE_NAME:
        return 'badge';
      case PropertyName.REPORTED_AT:
        return 'schedule';
      case PropertyName.LATITUDE:
        return 'share_location';
      case PropertyName.LONGITUDE:
        return 'share_location';
      case PropertyName.MOTION:
        return 'gesture';
      case PropertyName.VELOCITY:
        return 'speed';
      case PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER:
        return 'opacity';
      case PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE:
        return 'opacity';
      case PropertyName.AIR_PRESSURE:
        return 'compress';
      case PropertyName.TEMPERATURE:
        return 'thermostat';
      case PropertyName.AQI:
        return 'air';
      case PropertyName.ALTITUDE:
        return 'terrain';
      case PropertyName.SOIL_MOISTURE:
        return 'grass';
      case PropertyName.ILLUMINANCE:
        return 'wb_sunny';
      case PropertyName.BATTERY_PERCENTAGE:
        return 'battery_3_bar';
      case PropertyName.BATTERY_VOLTS:
        return 'battery_3_bar';
      case PropertyName.TRIGGER:
        return 'warning';
      case PropertyName.BATTERY_MAX_VOLTS:
        return 'battery_full';
      case PropertyName.BATTERY_MIN_VOLTS:
        return 'battery_0_bar';
      case PropertyName.DISTANCE:
        return 'straighten';
      case PropertyName.MAX_DISTANCE:
        return 'straighten';
      case PropertyName.MIN_DISTANCE:
        return 'straighten';
      case PropertyName.SOIL_CONDUCTIVITY:
        return 'electric_bolt';
      case PropertyName.CO2:
        return 'co2';
      case PropertyName.WATER_PRESSURE:
        return 'water';
      case PropertyName.OCCUPATION:
        return 'fullscreen';
      case PropertyName.VOC:
        return 'volcano';
      case PropertyName.PM2_5:
        return 'volcano';
      case PropertyName.PM10:
        return 'volcano';
      case PropertyName.O3:
        return 'masks';
      case PropertyName.NO2:
        return 'masks';
      case PropertyName.NH3:
        return 'masks';
      case PropertyName.CO:
        return 'masks';
      case PropertyName.PH:
        return 'masks';
      case PropertyName.DEVICE_DOWNLINK:
        return 'download';
      case PropertyName.INVALID:
        return 'error';
    }
  }
}
