import {
  CreateIrrigationZoneParamsDTO,
  CreateIrrigationZoneResultDTO, DeleteIrrigationZoneParamsDTO, DeleteIrrigationZoneResultDTO,
  QueryIrrigationZonesResultDTO,
  QueryHistoryDataParamsDTO,
  QueryHistoryResultDTO,
  QueryLatestDataParamsDTO,
  QueryLatestDataResultDTO,
  SubscribeToDataParamsDTO,
  SubscribeToDataResultDTO,
  UpdateIrrigationZoneParamsDTO,
  UpdateIrrigationZoneResultDTO
} from "@frontend-services/smart-irrigation/dto";
import {
  Data, DeleteIrrigationZoneCommand,
  IrrigationZone,
  SensorDataHistory,
  UpdateIrrigationZoneCommand
} from "@frontend-services/smart-irrigation/model";
import {LatestDataQueryFilters} from "@frontend-services/smart-irrigation/model";
import {HistoryQueryFilters} from "@frontend-services/smart-irrigation/model";
import {CreateIrrigationZoneCommand} from "@frontend-services/smart-irrigation/model";
import {DataFilters} from "@frontend-services/smart-irrigation/model";
import {IrrigationZoneMapper} from "./IrrigationZoneMapper";
import {DataMapper} from "./DataMapper";
import {DataHistoryMapper} from "./DataHistoryMapper";

export class OperationsMapper {
  static fetchIrrigationZoneDtoToModel(dto: QueryIrrigationZonesResultDTO): Array<IrrigationZone> {
    return dto.fetchIrrigationZones.map(g => IrrigationZoneMapper.dtoToModel(g));
  }

  static fetchLatestDataDtoToModel(dto: QueryLatestDataResultDTO): Array<Data> {
    return dto.fetchLatestData.map(d => DataMapper.dtoToModel(d));
  }

  static latestDataFiltersModelToDto(model: LatestDataQueryFilters): QueryLatestDataParamsDTO {
    return {filters: {devices: model.devices.map(d => d.value), irrigationZones: model.gardens.map(g => g.value)}}
  }

  static historyFiltersModelToDto(model: HistoryQueryFilters): QueryHistoryDataParamsDTO {
    return {
      filters: {
        devices: model.devices.map(d => d.value),
        irrigationZones: model.gardens.map(g => g.value),
        endTime: Math.round(model.endTime.getTime() / 1000).toString(),
        startTime: Math.round(model.startTime.getTime() / 1000).toString(),
      }
    }
  }

  static fetchHistoryDtoToModel(data: QueryHistoryResultDTO): Array<SensorDataHistory> {
    return data.history.map(h => DataHistoryMapper.dtoToModel(h));
  }

  static createIrrigationZoneDtoToModel(dto: CreateIrrigationZoneResultDTO): IrrigationZone {
    return IrrigationZoneMapper.dtoToModel(dto.createIrrigationZone);
  }

  static createIrrigationZoneInstructions(model: CreateIrrigationZoneCommand): CreateIrrigationZoneParamsDTO {
    const area = model.area.map(b => {
        return {
          position: b.position,
          longitude: b.point.longitude,
          latitude: b.point.latitude,
          altitude: b.point.altitude
        };
      }
    )
    return {instructions: {area, name: model.name.value}}
  }

  static deleteIrrigationZoneInstructions(model: DeleteIrrigationZoneCommand): DeleteIrrigationZoneParamsDTO {
    return {instructions: {id: model.id.value}}
  }

  static updateIrrigationZoneDtoToModel(dto: UpdateIrrigationZoneResultDTO): IrrigationZone {
    return IrrigationZoneMapper.dtoToModel(dto.updateIrrigationZone);
  }

  static deleteIrrigationZoneDtoToModel(dto: DeleteIrrigationZoneResultDTO): IrrigationZone {
    return IrrigationZoneMapper.dtoToModel(dto.deleteIrrigationZone);
  }

  static updateIrrigationZoneInstructions(model: UpdateIrrigationZoneCommand): UpdateIrrigationZoneParamsDTO {
    const area = model.area.map(b => {
        return {
          position: b.position,
          longitude: b.point.longitude,
          latitude: b.point.latitude,
          altitude: b.point.altitude
        };
      }
    )
    return {instructions: {id: model.id.value, area, name: model.name.value}}
  }

  static dtoToModel(data: SubscribeToDataResultDTO): Data {
    return DataMapper.dtoToModel(data.data);
  }

  static dataFiltersModelToDto(filters: DataFilters): SubscribeToDataParamsDTO {
    return {
      devices: filters.devices.map(d => d.value),
      irrigationZones: filters.gardens.map(g => g.value),
      content: filters.content
    };
  }

  static switchValve(deviceId: string) {
    return {
      instructions: {
        id: deviceId
      }
    }
  }
}
