import {AreaBoundary, GardeningAreaId, GardeningAreaName} from "@frontend-services/smart-irrigation/model";

export class UpdateGardeningAreaCommand {
  constructor(public id: GardeningAreaId, public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }
}
