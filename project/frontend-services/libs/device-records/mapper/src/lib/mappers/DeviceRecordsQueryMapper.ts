import { RecordMapper } from './RecordMapper';
import { DeviceRecordQuery } from '@frontend-services/device-records/dto';
import { DeviceRecord } from '@frontend-services/device-records/model';

export class DeviceRecordsQueryMapper {
  static dtoToModel(dto: DeviceRecordQuery): Array<DeviceRecord> {
    return dto.deviceRecords.map((e) => RecordMapper.dtoToModel(e));
  }
}
