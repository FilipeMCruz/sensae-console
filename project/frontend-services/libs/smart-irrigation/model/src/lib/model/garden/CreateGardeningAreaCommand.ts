import {GardeningAreaName} from "./GardeningAreaName";
import {AreaBoundary} from "./AreaBoundary";
import {GPSDataDetails} from "../details/GPSDataDetails";
import {Position} from "geojson";

export class CreateGardeningAreaCommand {
  constructor(public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }

  static build(name: string, coordinates: Position[]) {
    const area: Array<AreaBoundary> = [];
    coordinates.forEach((value: Position, index: number, array) => {
      if (array.length != index + 1)
        area.push(new AreaBoundary(index, GPSDataDetails.fromCoordinates(value)));
    })
    return new CreateGardeningAreaCommand(new GardeningAreaName(name), area);
  }
}
