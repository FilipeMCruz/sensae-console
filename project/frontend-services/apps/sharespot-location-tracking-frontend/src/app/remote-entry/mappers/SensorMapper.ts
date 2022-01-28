import {
  GPSSegmentDetailsDTO,
  GPSSegmentType,
  GPSStepDetailsDTO,
  HistorySensorDTO,
  SensorDataDTO
} from '../dtos/SensorDTO';
import {DeviceCoordinates} from '../model/DeviceCoordinates';
import {DeviceData} from "../model/DeviceData";
import {RecordEntry} from "../model/RecordEntry";
import {Device} from "../model/Device";
import {DeviceStatus, MotionType} from "../model/DeviceStatus";
import {DeviceDataDetails} from "../model/DeviceDataDetails";
import {DeviceHistorySegment} from "../model/DeviceHistorySegment";
import {DeviceHistory} from "../model/DeviceHistory";
import {DeviceHistorySegmentType} from "../model/DeviceHistorySegmentType";
import {DeviceHistoryStep} from "../model/DeviceHistoryStep";

export class SensorMapper {

  static dtoToModel(value: SensorDataDTO): DeviceData {
    const coordinates = new DeviceCoordinates(value.data.gps.latitude, value.data.gps.longitude);
    let status;
    if (value.data.status.motion == "ACTIVE") {
      status = new DeviceStatus(MotionType.ACTIVE);
    } else if (value.data.status.motion == "INACTIVE") {
      status = new DeviceStatus(MotionType.INACTIVE);
    } else {
      status = new DeviceStatus(MotionType.UNKWOWN);
    }
    const details = new DeviceDataDetails(coordinates, status);
    const entries = value.device.records.map(e => new RecordEntry(e.label, e.content));
    const sensor = new Device(value.device.id, value.device.name, entries);
    return new DeviceData(value.dataId, sensor, new Date(Number(value.reportedAt)), details);
  }

  static dtoToModelHistory(dto: HistorySensorDTO): Array<DeviceHistory> {
    return dto.history.map(h => {
      const segments = h.segments.map(d => this.dtoToModelSeg(d));
      return new DeviceHistory(h.deviceName,
        h.deviceId,
        Number(h.startTime),
        Number(h.endTime),
        +h.distance.toFixed(2),
        segments);
    });
  }

  static dtoToModelSeg(dto: GPSSegmentDetailsDTO): DeviceHistorySegment {
    let type;
    if (dto.type === GPSSegmentType.INACTIVE) {
      type = DeviceHistorySegmentType.INACTIVE;
    } else if (dto.type === GPSSegmentType.UNKNOWN_INACTIVE) {
      type = DeviceHistorySegmentType.UNKNOWN_INACTIVE;
    } else if (dto.type === GPSSegmentType.ACTIVE) {
      type = DeviceHistorySegmentType.ACTIVE;
    } else {
      type = DeviceHistorySegmentType.UNKNOWN_ACTIVE;
    }

    const steps = dto.steps.map(step => this.dtoToModelStep(step));

    return new DeviceHistorySegment(type, steps)
  }

  static dtoToModelStep(dto: GPSStepDetailsDTO): DeviceHistoryStep {
    let status;
    if (dto.status.motion == "ACTIVE") {
      status = new DeviceStatus(MotionType.ACTIVE);
    } else if (dto.status.motion == "INACTIVE") {
      status = new DeviceStatus(MotionType.INACTIVE);
    } else {
      status = new DeviceStatus(MotionType.UNKWOWN);
    }
    const coordinates = new DeviceCoordinates(dto.gps.latitude, dto.gps.longitude);
    return new DeviceHistoryStep(coordinates, status, new Date(Number(dto.reportedAt)))
  }
}
