import {GPSSensorDataQuery} from "../dtos/SensorDTO";
import {DeviceHistoryQuery} from "../model/pastdata/DeviceHistoryQuery";

export class DeviceHistoryMapper {

  static modelToDto(value: DeviceHistoryQuery): GPSSensorDataQuery {
    return {
      device: value.devices.map(d => d.id),
      endTime: Math.round(value.endTime.getTime() / 1000).toString(),
      startTime: Math.round(value.startTime.getTime() / 1000).toString()
    }
  }
}
