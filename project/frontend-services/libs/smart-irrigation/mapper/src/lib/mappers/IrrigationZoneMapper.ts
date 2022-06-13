import {IrrigationZoneDTO} from "@frontend-services/smart-irrigation/dto";
import {
  AreaBoundary,
  IrrigationZone,
  IrrigationZoneId,
  IrrigationZoneName,
  GPSDataDetails
} from "@frontend-services/smart-irrigation/model";

export class IrrigationZoneMapper {
  static dtoToModel(dto: IrrigationZoneDTO): IrrigationZone {
    const area = dto.area.map(b => new AreaBoundary(b.position, new GPSDataDetails(b.latitude, b.longitude, b.altitude)));
    return new IrrigationZone(new IrrigationZoneId(dto.id), new IrrigationZoneName(dto.name), area);
  }
}
