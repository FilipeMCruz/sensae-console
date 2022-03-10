import {
  DeviceCoordinates,
  DeviceHistory,
  DeviceHistoryQuery,
  DeviceHistorySegment,
  DeviceHistorySegmentType,
  DeviceHistoryStep,
  DeviceStatus,
  HistoryColorSet,
  MotionType,
} from '@frontend-services/fleet-management/model';
import {
  GPSSegmentDetailsDTO,
  GPSSegmentType,
  GPSSensorDataQuery,
  GPSStepDetailsDTO,
  HistorySensorDTO,
} from '@frontend-services/fleet-management/dto';

export class DevicePastDataMapper {
  static modelToDto(value: DeviceHistoryQuery): GPSSensorDataQuery {
    return {
      device: value.devices.map((d) => d.id),
      endTime: Math.round(value.endTime.getTime() / 1000).toString(),
      startTime: Math.round(value.startTime.getTime() / 1000).toString(),
    };
  }

  static dtoToModel(dto: HistorySensorDTO): Array<DeviceHistory> {
    return dto.history.map((h, index) => {
      const segments = h.segments.map((d) => this.dtoToModelSeg(d));
      return new DeviceHistory(
        h.deviceName,
        h.deviceId,
        Number(h.startTime),
        Number(h.endTime),
        +h.distance.toFixed(2),
        segments,
        HistoryColorSet.get(index)
      );
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

    const steps = dto.steps.map((step) => this.dtoToModelStep(step));

    return new DeviceHistorySegment(type, steps);
  }

  static dtoToModelStep(dto: GPSStepDetailsDTO): DeviceHistoryStep {
    let status;
    if (dto.status.motion == 'ACTIVE') {
      status = new DeviceStatus(MotionType.ACTIVE);
    } else if (dto.status.motion == 'INACTIVE') {
      status = new DeviceStatus(MotionType.INACTIVE);
    } else {
      status = new DeviceStatus(MotionType.UNKWOWN);
    }
    const coordinates = new DeviceCoordinates(
      dto.gps.latitude,
      dto.gps.longitude
    );
    return new DeviceHistoryStep(coordinates, status, Number(dto.reportedAt));
  }
}
