import { DeviceCoordinates } from '../DeviceCoordinates';
import { DeviceStatus } from '../DeviceStatus';
import { Feature } from 'geojson';

export class DeviceHistoryStep {
  constructor(
    public gps: DeviceCoordinates,
    public status: DeviceStatus,
    public reportedAt: number
  ) {}

  getDate() {
    return new Date(this.reportedAt);
  }

  toFeature(): Feature {
    return {
      type: 'Feature',
      properties: {
        reportedAt: this.reportedAt,
      },
      geometry: {
        type: 'Point',
        coordinates: this.gps.toCoordinates(),
      },
    };
  }
}
