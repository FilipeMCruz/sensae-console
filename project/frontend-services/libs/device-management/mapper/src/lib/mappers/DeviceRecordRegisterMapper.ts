import {DeviceInformationMapper} from './DeviceInformationMapper';
import {DeviceRecordsInput, DeviceRecordsInputResult} from '@frontend-services/device-management/dto';
import {DeviceInformation} from '@frontend-services/device-management/model';

export class DeviceRecordRegisterMapper {
  static modelToDto(model: DeviceInformation): DeviceRecordsInput {
    return {index: DeviceInformationMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DeviceRecordsInputResult): DeviceInformation {
    return DeviceInformationMapper.dtoToModel(dto.index);
  }
}
