import { SensorDataDTO } from '../dtos/SensorDTO';
import { DeviceCoordinates } from '../model/DeviceCoordinates';
import { DeviceData } from '../model/livedata/DeviceData';
import { RecordEntry } from '../model/livedata/RecordEntry';
import { Device } from '../model/Device';
import { DeviceStatus, MotionType } from '../model/DeviceStatus';
import { DeviceDataDetails } from '../model/livedata/DeviceDataDetails';

export class DeviceLiveDataMapper {
  static dtoToModel(value: SensorDataDTO): DeviceData {
    const coordinates = new DeviceCoordinates(
      value.data.gps.latitude,
      value.data.gps.longitude
    );
    let status;
    if (value.data.status.motion == 'ACTIVE') {
      status = new DeviceStatus(MotionType.ACTIVE);
    } else if (value.data.status.motion == 'INACTIVE') {
      status = new DeviceStatus(MotionType.INACTIVE);
    } else {
      status = new DeviceStatus(MotionType.UNKWOWN);
    }
    const details = new DeviceDataDetails(coordinates, status);
    const entries = value.device.records.map(
      (e) => new RecordEntry(e.label, e.content)
    );
    const sensor = new Device(value.device.id, value.device.name, entries);
    return new DeviceData(
      value.dataId,
      sensor,
      new Date(Number(value.reportedAt)),
      details
    );
  }
}
