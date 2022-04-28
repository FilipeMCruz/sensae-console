import {RecordMapper} from './RecordMapper';
import {DeviceRecordsInput} from '@frontend-services/device-management/dto';
import {DeviceInformation} from '@frontend-services/device-management/model';

export class DeviceRecordRegisterMapper {
  static modelToDto(model: DeviceInformation): DeviceRecordsInput {
    return {index: RecordMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DeviceRecordsInput): DeviceInformation {
    return RecordMapper.dtoToModel(dto.index);
  }
}
