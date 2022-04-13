import {Device} from "./Device";
import {SensorDataDetails} from "../details/SensorDataDetails";
import {ParkSensorDataDetails} from "../details/ParkSensorDataDetails";
import {StoveSensorDataDetails} from "../details/StoveSensorDataDetails";
import {ValveDataDetails} from "../details/ValveDataDetails";

export class Data {
  constructor(public id: string, public device: Device, public reportedAt: Date, public data: SensorDataDetails) {
  }

  public update(update: Data) {
    this.id = update.id;
    this.device = update.device;
    this.reportedAt = update.reportedAt;
    this.data = update.data;
  }

  getColor() {
    if (this.data instanceof ParkSensorDataDetails) {
      return {'color': '#656d4a'};
    } else if (this.data instanceof StoveSensorDataDetails) {
      return {'color': '#a68a64'}
    } else if (this.data instanceof ValveDataDetails) {
      return {'color': '#adb5bd'}
    } else {
      return {'color': '#adb5bd'}
    }
  }

  getFirstDataDetails(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return "Soil Moisture: " + this.data.soilMoisture.percentage + "%";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return "Temperature: " + this.data.temperature.celsius + "ºC";
    } else if (this.data instanceof ValveDataDetails) {
      return "Status: " + this.data.valve.status.toString();
    } else {
      return "";
    }
  }

  getSecondDataDetails(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return "Illuminance: " + this.data.illuminance.lux;
    } else if (this.data instanceof StoveSensorDataDetails) {
      return "Air Humidity: " + this.data.humidity.gramsPerCubicMeter + "g/m3";
    } else {
      return "";
    }
  }
}
