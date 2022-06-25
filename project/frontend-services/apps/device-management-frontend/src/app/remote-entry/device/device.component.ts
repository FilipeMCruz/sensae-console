import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {
  DeviceCommand,
  DeviceInformation,
  DeviceViewType,
  EntryViewType,
  RecordEntry,
  SensorDataRecordLabel,
  StaticDataEntry,
  SubDevice,
} from '@frontend-services/device-management/model';
import {CommandDeviceService} from "@frontend-services/device-management/services";
import {MatSnackBar} from "@angular/material/snack-bar";

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
  @Input() canCommand = false;
  @Input() devices: DeviceInformation[] = [];

  @Output() newDeviceEvent = new EventEmitter<DeviceInformation>();
  @Output() deleteDeviceEvent = new EventEmitter<DeviceInformation>();

  device = DeviceInformation.empty();
  currentEntry = RecordEntry.empty();
  currentStaticData = StaticDataEntry.empty();
  currentSubDevice = SubDevice.empty();
  currentCommand = DeviceCommand.empty();

  deviceView = DeviceViewType.New;
  deviceViewType = DeviceViewType;

  entryView = EntryViewType.List;
  subDeviceView = EntryViewType.List;
  commandsView = EntryViewType.List;

  viewType = EntryViewType;

  sensorDataType: Array<string> = Object.values(SensorDataRecordLabel);

  currentRecordEntryIndex = -1;
  currentStaticDataEntryIndex = -1;
  currentSubDeviceIndex = -1;
  currentCommandIndex = -1;

  constructor(private service: CommandDeviceService, private _snackBar: MatSnackBar) {
  }

  ngOnChanges(): void {
    if (this.deviceEntry) {
      this.deviceView = DeviceViewType.Edit;
      this.device = this.deviceEntry.clone();
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
      this.device.records.push(this.currentEntry);
      this.refreshEntries();
    }
  }

  saveEntryEdit() {
    if (this.currentEntry.isValid()) {
      this.device.records[this.currentRecordEntryIndex] = this.currentEntry;
      this.refreshEntries();
    }
  }

  removeEntry(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.device.records.splice(index, 1);
    this.refreshEntries();
  }

  editEntry(index: number) {
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentRecordEntryIndex = index;
      this.currentEntry = this.device.records[index].clone();
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
    const usedSensorDataLabels = this.device.staticData
      .map((e: StaticDataEntry) => e.label.toString());

    this.sensorDataType = Object.values(SensorDataRecordLabel).filter(
      (v) => !usedSensorDataLabels.includes(v)
    );
  }

  private clearEntryFields() {
    this.currentEntry = RecordEntry.empty();
    this.currentStaticData = StaticDataEntry.empty();
  }

  editDevice(index: number) {
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

  editStaticData(index: number) {
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentStaticDataEntryIndex = index;
      this.currentStaticData = this.device.staticData[index].clone();
      this.entryView = EntryViewType.Edit;
    }
  }

  removeStaticData(event: MouseEvent, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.device.staticData.splice(index, 1);
    this.refreshEntries();
  }

  saveStaticDataEdit() {
    if (this.currentStaticData.isValid()) {
      this.device.staticData[this.currentStaticDataEntryIndex] = this.currentStaticData;
      this.refreshEntries();
    }
  }

  addStaticData() {
    if (this.currentStaticData.isValid()) {
      this.device.staticData.push(this.currentStaticData);
      this.refreshEntries();
    }
  }

  newStaticData() {
    this.entryView = this.viewType.New;
    const usedSensorDataLabels = this.device.staticData
      .map((e: StaticDataEntry) => e.label.toString());

    this.sensorDataType = Object.values(SensorDataRecordLabel)
      .filter((v) => !usedSensorDataLabels.includes(v))
      .filter(v => v !== SensorDataRecordLabel.ERROR);
  }

  sendCommand($event: MouseEvent, index: number) {
    this.service.command(this.device.commands[index].clone(), this.device.device)
      .subscribe(() => this._snackBar.open("Command Sent", undefined, {
        horizontalPosition: 'right',
        verticalPosition: 'bottom',
        duration: 5000
      }));
  }

  canSendCommand(index: number): boolean {
    return this.device.device.downlink.trim().length !== 0 && this.deviceEntry?.commands[index] !== undefined;
  }
}
