import { RecordMapper } from './RecordMapper';
import { DeviceRecordQuery } from '@frontend-services/device-management/dto';
import { DeviceInformation } from '@frontend-services/device-management/model';

export class DeviceRecordsQueryMapper {
  static dtoToModel(dto: DeviceRecordQuery): Array<DeviceInformation> {
    return dto.deviceRecords.map((e) => RecordMapper.dtoToModel(e));
  }
}
