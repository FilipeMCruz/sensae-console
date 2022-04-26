import { RecordMapper } from './RecordMapper';
import { DeviceRecordsInput } from '@frontend-services/device-management/dto';
import { DeviceRecord } from '@frontend-services/device-management/model';

export class DeviceRecordRegisterMapper {
  static modelToDto(model: DeviceRecord): DeviceRecordsInput {
    return { index: RecordMapper.modelToDto(model) };
  }

  static dtoToModel(dto: DeviceRecordsInput): DeviceRecord {
    return RecordMapper.dtoToModel(dto.index);
  }
}
