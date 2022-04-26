import { DeviceRecord } from './DeviceRecord';

export class DeviceRecordPair {
  constructor(public fresh: DeviceRecord, public old: DeviceRecord) {}
}
