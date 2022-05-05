import {Position} from "geojson";
import {GardeningAreaId} from "./GardeningAreaId";
import {GardeningAreaName} from "./GardeningAreaName";
import {AreaBoundary} from "./AreaBoundary";
import {GPSDataDetails} from "../details/GPSDataDetails";

export class UpdateGardeningAreaCommand {
  constructor(public id: GardeningAreaId, public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }

  static build(id: string, name: string, coordinates: Position[]) {
    const area: Array<AreaBoundary> = [];
    coordinates.forEach((value: Position, index: number, array) => {
      if (array.length != index + 1)
        area.push(new AreaBoundary(index, GPSDataDetails.fromCoordinates(value)));
    })
    return new UpdateGardeningAreaCommand(new GardeningAreaId(id), new GardeningAreaName(name), area);
  }
}
