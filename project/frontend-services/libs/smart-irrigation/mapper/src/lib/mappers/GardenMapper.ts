import {GardeningAreaDTO} from "@frontend-services/smart-irrigation/dto";
import {
  AreaBoundary,
  GardeningArea,
  GardeningAreaId,
  GardeningAreaName,
  GPSDataDetails
} from "@frontend-services/smart-irrigation/model";

export class GardenMapper {
  static dtoToModel(dto: GardeningAreaDTO): GardeningArea {
    const area = dto.area.map(b => new AreaBoundary(b.position, new GPSDataDetails(b.latitude, b.longitude, b.altitude)));
    return new GardeningArea(new GardeningAreaId(dto.id), new GardeningAreaName(dto.name), area);
  }
}
