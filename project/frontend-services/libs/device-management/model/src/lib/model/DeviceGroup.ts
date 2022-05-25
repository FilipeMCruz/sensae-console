import {groupBy} from "@frontend-services/core";
import {RecordEntry} from "./RecordEntry";
import {RecordEntryType} from "./RecordEntryType";
import {DeviceInformation} from "./DeviceInformation";
import {Device} from "./Device";

export class DeviceGroup {
  constructor(public entry: RecordEntry, public devices: Array<DeviceInformation>) {
    if (entry.type === RecordEntryType.SENSOR_DATA) {
      this.devices = [];
    }
    this.devices = this.devices.sort((a, b) => a.device.name.localeCompare(b.device.name));
  }

  static buildGroups(label: string, devices: Array<DeviceInformation>): Array<DeviceGroup> {
    const result = [];
    if (label === "") {
      result.push(new DeviceGroup(new RecordEntry("None", "Missing Label", RecordEntryType.BASIC), devices))
    } else {
      const groups = groupBy(devices, d => d.entries.find(e => e.label.localeCompare(label, undefined, {sensitivity: 'accent'}) === 0)?.content.toLowerCase(), () => "Missing Label");
      for (const groupsKey in groups) {
        result.push(new DeviceGroup(new RecordEntry(label, groupsKey, RecordEntryType.BASIC), groups[groupsKey]))
      }
    }
    return result.sort((a, b) => a.entry.content.localeCompare(b.entry.content));
  }

  public tryAdd(device: DeviceInformation) {
    this.devices = this.devices.filter(r => r.device.id != device.device.id);
    if (this.entry.label === "") {
      this.devices.push(device);
    } else {
      const recordContent = device.entries.find(e => e.label === this.entry.label)?.content;
      if (recordContent && recordContent === this.entry.content) {
        this.devices.push(device);
      } else if (!recordContent && this.entry.content === "Missing Label") {
        this.devices.push(device);
      }
    }
    this.devices = this.devices.sort((a, b) => a.device.name.localeCompare(b.device.name));
  }

  public tryRemove(device: Device) {
    this.devices = this.devices.filter((r) => r.device.id != device.id)
  }
}
