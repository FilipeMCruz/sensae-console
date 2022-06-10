import {IrrigationZoneName} from "./IrrigationZoneName";
import {AreaBoundary} from "./AreaBoundary";
import {GPSDataDetails} from "../details/GPSDataDetails";
import {Position} from "geojson";

export class CreateIrrigationZoneCommand {
  constructor(public name: IrrigationZoneName, public area: Array<AreaBoundary>) {
  }

  static build(name: string, coordinates: Position[]) {
    const area: Array<AreaBoundary> = [];
    coordinates.forEach((value: Position, index: number, array) => {
      if (array.length != index + 1)
        area.push(new AreaBoundary(index, GPSDataDetails.fromCoordinates(value)));
    })
    return new CreateIrrigationZoneCommand(new IrrigationZoneName(name), area);
  }
}
