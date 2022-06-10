import {IrrigationZoneId} from "@frontend-services/smart-irrigation/model";

export class DeleteIrrigationZoneCommand {
  constructor(public id: IrrigationZoneId) {
  }
}
