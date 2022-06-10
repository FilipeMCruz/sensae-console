import {Position} from "geojson";
import {IrrigationZoneId} from "./IrrigationZoneId";
import {IrrigationZoneName} from "./IrrigationZoneName";
import {AreaBoundary} from "./AreaBoundary";
import {GPSDataDetails} from "../details/GPSDataDetails";

export class UpdateIrrigationZoneCommand {
  constructor(public id: IrrigationZoneId, public name: IrrigationZoneName, public area: Array<AreaBoundary>) {
  }

  static build(id: string, name: string, coordinates: Position[]) {
    const area: Array<AreaBoundary> = [];
    coordinates.forEach((value: Position, index: number, array) => {
      if (array.length != index + 1)
        area.push(new AreaBoundary(index, GPSDataDetails.fromCoordinates(value)));
    })
    return new UpdateIrrigationZoneCommand(new IrrigationZoneId(id), new IrrigationZoneName(name), area);
  }
}
