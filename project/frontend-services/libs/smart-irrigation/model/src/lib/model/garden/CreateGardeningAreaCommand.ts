import {AreaBoundary, GardeningAreaName} from "@frontend-services/smart-irrigation/model";

export class CreateGardeningAreaCommand {
  constructor(public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }
}
