import {
  CreateGardenParamsDTO,
  CreateGardenResultDTO,
  QueryGardensResultDTO,
  QueryHistoryDataParamsDTO,
  QueryHistoryResultDTO,
  QueryLatestDataParamsDTO,
  QueryLatestDataResultDTO,
  SubscribeToDataParamsDTO,
  SubscribeToDataResultDTO,
  UpdateGardenParamsDTO,
  UpdateGardenResultDTO
} from "@frontend-services/smart-irrigation/dto";
import {
  Data,
  GardeningArea,
  SensorDataHistory,
  UpdateGardeningAreaCommand
} from "@frontend-services/smart-irrigation/model";
import {LatestDataQueryFilters} from "@frontend-services/smart-irrigation/model";
import {HistoryQueryFilters} from "@frontend-services/smart-irrigation/model";
import {CreateGardeningAreaCommand} from "@frontend-services/smart-irrigation/model";
import {DataFilters} from "@frontend-services/smart-irrigation/model";
import {GardenMapper} from "./GardenMapper";
import {DataMapper} from "./DataMapper";
import {DataHistoryMapper} from "./DataHistoryMapper";

export class OperationsMapper {
  static fetchGardenDtoToModel(dto: QueryGardensResultDTO): Array<GardeningArea> {
    return dto.fetchGardens.map(g => GardenMapper.dtoToModel(g));
  }

  static fetchLatestDataDtoToModel(dto: QueryLatestDataResultDTO): Array<Data> {
    return dto.fetchLatestData.map(d => DataMapper.dtoToModel(d));
  }

  static latestDataFiltersModelToDto(model: LatestDataQueryFilters): QueryLatestDataParamsDTO {
    return {filters: {devices: model.devices.map(d => d.value), gardens: model.gardens.map(g => g.value)}}
  }

  static historyFiltersModelToDto(model: HistoryQueryFilters): QueryHistoryDataParamsDTO {
    return {
      filters: {
        devices: model.devices.map(d => d.value),
        gardens: model.gardens.map(g => g.value),
        endTime: model.endTime.getTime().toString(),
        startTime: model.startTime.getTime().toString()
      }
    }
  }

  static fetchHistoryDtoToModel(data: QueryHistoryResultDTO): Array<SensorDataHistory> {
    return data.history.map(h => DataHistoryMapper.dtoToModel(h));
  }

  static createGardenDtoToModel(dto: CreateGardenResultDTO): GardeningArea {
    return GardenMapper.dtoToModel(dto.createGarden);
  }

  static createGardenInstructions(model: CreateGardeningAreaCommand): CreateGardenParamsDTO {
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

  static updateGardenDtoToModel(dto: UpdateGardenResultDTO): GardeningArea {
    return GardenMapper.dtoToModel(dto.updateGarden);
  }

  static updateGardenInstructions(model: UpdateGardeningAreaCommand): UpdateGardenParamsDTO {
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
      gardens: filters.gardens.map(g => g.value),
      content: filters.content
    };
  }
}
