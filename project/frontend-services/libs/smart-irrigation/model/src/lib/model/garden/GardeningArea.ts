import {AreaBoundary} from "./AreaBoundary";
import {GardeningAreaName} from "./GardeningAreaName";
import {GardeningAreaId} from "./GardeningAreaId";

export class GardeningArea {
  constructor(public id: GardeningAreaId, public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }
}
