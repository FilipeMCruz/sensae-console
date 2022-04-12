import {DeviceType} from "../device/DeviceType";
import {DeviceLedgerHistoryEntry} from "./DeviceLedgerHistoryEntry";

export class SensorDataHistory {
  constructor(public id: string, public type: DeviceType, public ledger: Array<DeviceLedgerHistoryEntry>) {
  }
}
