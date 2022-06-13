import {DeviceInformationMapper} from './DeviceInformationMapper';
import {DeviceRecordsInput} from '@frontend-services/device-management/dto';
import {DeviceInformation} from '@frontend-services/device-management/model';

export class DeviceRecordRegisterMapper {
  static modelToDto(model: DeviceInformation): DeviceRecordsInput {
    return {index: DeviceInformationMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DeviceRecordsInput): DeviceInformation {
    return DeviceInformationMapper.dtoToModel(dto.index);
  }
}
