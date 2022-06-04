import { DeviceInformationMapper } from './DeviceInformationMapper';
import { DeviceInformationQuery } from '@frontend-services/device-management/dto';
import { DeviceInformation } from '@frontend-services/device-management/model';

export class DeviceInformationQueryMapper {
  static dtoToModel(dto: DeviceInformationQuery): Array<DeviceInformation> {
    return dto.deviceInformation.map((e) => DeviceInformationMapper.dtoToModel(e));
  }
}
