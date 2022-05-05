import {GardeningAreaId} from "@frontend-services/smart-irrigation/model";

export class DeleteGardeningAreaCommand {
  constructor(public id: GardeningAreaId) {
  }
}
