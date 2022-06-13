export class ChartConfigs {
  constructor(public color: string, public name: string, public units: string, public minValue: number, public maxValue: number) {
  }

  public static getTemperatureConfig() {
    return new ChartConfigs("#ee9b00", "Temperature", "ºC", 0, 50);
  }

  public static getAirHumidityConfig() {
    return new ChartConfigs("#34a0a4", "AirHumidity", "%", 0, 100);
  }

  public static getIlluminanceConfig() {
    return new ChartConfigs("#f9c74f", "Illuminance", "lux", 0, 100000);
  }

  public static getSoilMoistureConfig() {
    return new ChartConfigs("#656d4a", "Soil Moisture", "%", 0, 100);
  }
}
