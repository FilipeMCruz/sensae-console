import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {
  DeviceCommand,
  DeviceInformation,
  DeviceViewType,
  EntryViewType,
  RecordEntry,
  RecordEntryType,
  SensorDataRecordLabel, SubDevice,
} from '@frontend-services/device-management/model';

@Component({
  selector: 'frontend-services-device-record',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.scss'],
})
export class DeviceComponent implements OnChanges {
  @Input() deviceEntry!: DeviceInformation;
  @Input() deviceViewEntry!: DeviceViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;
  @Input() devices: DeviceInformation[] = [];

  @Output() newDeviceEvent = new EventEmitter<DeviceInformation>();
  @Output() deleteDeviceEvent = new EventEmitter<DeviceInformation>();

  device = DeviceInformation.empty();
  currentEntry = RecordEntry.empty();
  currentSubDevice = SubDevice.empty();
  currentCommand = DeviceCommand.empty();

  deviceView = DeviceViewType.New;
  deviceViewType = DeviceViewType;

  entryView = EntryViewType.List;
  subDeviceView = EntryViewType.List;
  commandsView = EntryViewType.List;

  viewType = EntryViewType;

  typeType = RecordEntryType;

  sensorDataType: Array<string> = Object.values(SensorDataRecordLabel);
  recordType: Array<string> = Object.values(RecordEntryType);

  currentEntryIndex = -1;
  currentSubDeviceIndex = -1;
  currentCommandIndex = -1;

  ngOnChanges(): void {
    if (this.deviceEntry) {
      this.deviceView = DeviceViewType.Edit;
      this.device = this.deviceEntry;
      this.resetOptions();
    }
    if (this.deviceViewEntry) {
      this.deviceView = this.deviceViewEntry;
    }
  }

  saveDevice() {
    this.newDeviceEvent.emit(this.device);
    this.resetView();
  }

  deleteDevice() {
    this.deleteDeviceEvent.emit(this.device);
    this.resetView();
  }

  addEntry() {
    if (this.currentEntry.isValid()) {
      this.device.entries.push(this.currentEntry);
      this.refreshEntries();
    }
  }

  saveEntryEdit() {
    if (this.currentEntry.isValid()) {
      this.device.entries[this.currentEntryIndex] = this.currentEntry;
      this.refreshEntries();
    }
  }

  removeEntry(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.device.entries.splice(index, 1);
    this.refreshEntries();
  }

  getEntryType(entry: RecordEntry) {
    return entry.type == RecordEntryType.SENSOR_DATA
      ? 'share_location'
      : 'visibility';
  }

  editEntry(index: number) {
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentEntryIndex = index;
      this.currentEntry = this.device.entries[index].clone();
      this.entryView = EntryViewType.Edit;
    }
  }

  private resetView() {
    this.deviceView = this.deviceViewType.New;
    this.device = DeviceInformation.empty();
    this.resetOptions();
  }

  private refreshEntries() {
    this.resetOptions();
    this.entryView = EntryViewType.List;
    this.clearEntryFields();
  }

  private resetOptions() {
    const usedSensorDataLabels = this.device.entries
      .filter((e: RecordEntry) => e.type == RecordEntryType.SENSOR_DATA)
      .map((e: RecordEntry) => e.label.toString());

    this.sensorDataType = Object.values(SensorDataRecordLabel).filter(
      (v) => !usedSensorDataLabels.includes(v)
    );
    if (this.sensorDataType.length == 0) {
      this.recordType = Object.values(RecordEntryType).filter(
        (v) => v != RecordEntryType.SENSOR_DATA.toString()
      );
    } else {
      this.recordType = Object.values(RecordEntryType);
    }
  }

  private clearEntryFields() {
    this.currentEntry = RecordEntry.empty();
  }

  editDevice(index: number) {
    console.log(this.device)
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentSubDeviceIndex = index;
      this.currentSubDevice = this.device.subDevices[index].clone();
      this.subDeviceView = EntryViewType.Edit;
    }
  }

  addSubDevice() {
    if (this.currentSubDevice.isValid()) {
      this.device.subDevices.push(this.currentSubDevice);
      this.refreshSubDevices();
    }
  }

  saveSubDeviceEdit() {
    if (this.currentSubDevice.isValid()) {
      this.device.subDevices[this.currentSubDeviceIndex] = this.currentSubDevice;
      this.refreshEntries();
    }
  }

  removeSubDevice(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.device.subDevices.splice(index, 1);
    this.refreshEntries();
  }

  private refreshSubDevices() {
    this.resetOptions();
    this.subDeviceView = EntryViewType.List;
    this.clearSubDeviceFields();
  }

  private clearSubDeviceFields() {
    this.currentSubDevice = SubDevice.empty();
  }

  getDeviceName(id: string) {
    return this.devices.find(d => d.device.id === id)?.device.name
  }

  addCommand() {
    if (this.currentCommand.isValid()) {
      this.device.commands.push(this.currentCommand);
      this.refreshCommands();
    }
  }

  saveCommand() {
    if (this.currentCommand.isValid()) {
      this.device.commands[this.currentCommandIndex] = this.currentCommand;
      this.refreshCommands();
    }
  }

  removeCommand(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.device.commands.splice(index, 1);
    this.refreshCommands();
  }

  editCommand(index: number) {
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentCommandIndex = index;
      this.currentCommand = this.device.commands[index].clone();
      this.commandsView = EntryViewType.Edit;
    }
  }

  private refreshCommands() {
    this.resetOptions();
    this.commandsView = EntryViewType.List;
    this.clearCommandFields();
  }

  private clearCommandFields() {
    this.currentCommand = DeviceCommand.empty();
  }
}
