import {SensorDataDTO} from "@frontend-services/smart-irrigation/dto";
import {Data} from "@frontend-services/smart-irrigation/model";
import {DeviceMapper} from "./DeviceMapper";
import {DataDetailsMapper} from "./DataDetailsMapper";

export class DataMapper {
  static dtoToModel(dto: SensorDataDTO): Data {
    return new Data(dto.dataId,
      DeviceMapper.dtoToModel(dto.device),
      new Date(Number(dto.reportedAt)),
      DataDetailsMapper.dataDtoToModel(dto.data, dto.device.type));
  }
}
