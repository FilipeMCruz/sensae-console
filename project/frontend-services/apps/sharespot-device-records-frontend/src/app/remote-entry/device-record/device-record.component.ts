import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
} from '@angular/core';
import {
  DeviceRecord,
  DeviceViewType,
  EntryViewType,
  RecordEntry,
  RecordEntryType,
  SensorDataRecordLabel,
} from '@frontend-services/device-records/model';

@Component({
  selector: 'frontend-services-device-record',
  templateUrl: './device-record.component.html',
  styleUrls: ['./device-record.component.scss'],
})
export class DeviceRecordComponent implements OnChanges {
  @Input() entry!: DeviceRecord;
  @Input() deviceViewEntry!: DeviceViewType;

  @Output() newDeviceEvent = new EventEmitter<DeviceRecord>();
  @Output() deleteDeviceEvent = new EventEmitter<DeviceRecord>();

  record = DeviceRecord.empty();
  currentEntry = RecordEntry.empty();

  deviceView = DeviceViewType.New;
  deviceViewType = DeviceViewType;

  entryView = EntryViewType.List;
  entryViewType = EntryViewType;

  typeType = RecordEntryType;

  sensorDataType: Array<string> = Object.values(SensorDataRecordLabel);
  recordType: Array<string> = Object.values(RecordEntryType);

  currentIndex = -1;

  ngOnChanges(): void {
    if (this.entry) {
      this.deviceView = DeviceViewType.Edit;
      this.record = this.entry;
      this.resetOptions();
    }
    if (this.deviceViewEntry) {
      this.deviceView = this.deviceViewEntry;
    }
  }

  saveDevice() {
    this.newDeviceEvent.emit(this.record);
    this.resetView();
  }

  deleteDevice() {
    this.deleteDeviceEvent.emit(this.record);
    this.resetView();
  }

  addEntry() {
    if (this.currentEntry.isValid()) {
      this.record.entries.push(this.currentEntry);
      this.refreshEntries();
    }
  }

  saveEntryEdit() {
    if (this.currentEntry.isValid()) {
      this.record.entries[this.currentIndex] = this.currentEntry;
      this.refreshEntries();
    }
  }

  removeEntry(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.record.entries.splice(index, 1);
    this.refreshEntries();
  }

  getEntryType(entry: RecordEntry) {
    return entry.type == RecordEntryType.SENSOR_DATA
      ? 'share_location'
      : 'visibility';
  }

  editEntry(index: number) {
    if (this.deviceView != this.deviceViewType.Compare) {
      this.currentIndex = index;
      this.currentEntry = this.record.entries[index];
      this.entryView = EntryViewType.Edit;
    }
  }

  private resetView() {
    this.deviceView = this.deviceViewType.New;
    this.record = DeviceRecord.empty();
    this.resetOptions();
  }

  private refreshEntries() {
    this.resetOptions();
    this.entryView = EntryViewType.List;
    this.clearEntryFields();
  }

  private resetOptions() {
    const usedSensorDataLabels = this.record.entries
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
}
