import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {
  DataTransformation,
  DataTransformationViewType,
  EntryViewType,
  PropertyName,
  PropertyTransformation,
} from '@frontend-services/data-processor/model';

@Component({
  selector: 'frontend-services-data-transformation',
  templateUrl: './data-transformation.component.html',
  styleUrls: ['./data-transformation.component.scss'],
})
export class DataTransformationComponent implements OnChanges {
  @Input() entry!: DataTransformation;
  @Input() dataTransformationViewEntry!: DataTransformationViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;

  @Output() newDataTransformationEvent = new EventEmitter<DataTransformation>();
  @Output() deleteDataTransformationEvent =
    new EventEmitter<DataTransformation>();

  dataTransformation = DataTransformation.empty();
  currentEntry = PropertyTransformation.empty();

  dataTransformationView = DataTransformationViewType.New;
  dataTransformationViewType = DataTransformationViewType;

  entryView = EntryViewType.List;
  entryViewType = EntryViewType;

  propertyNameTypes: Array<string> = Object.values(PropertyName);

  currentIndex = -1;

  ngOnChanges(): void {
    if (this.entry) {
      this.dataTransformationView = DataTransformationViewType.Edit;
      this.dataTransformation = this.entry;
      this.resetOptions();
    }
    if (this.dataTransformationViewEntry) {
      this.dataTransformationView = this.dataTransformationViewEntry;
    }
  }

  saveDataTransformation() {
    this.newDataTransformationEvent.emit(this.dataTransformation);
    this.resetView();
  }

  deleteDataTransformation() {
    this.deleteDataTransformationEvent.emit(this.dataTransformation);
    this.resetView();
  }

  addEntry() {
    if (this.currentEntry.isValid()) {
      this.dataTransformation.entries.push(this.currentEntry);
      this.refreshEntries();
    }
  }

  saveEntryEdit() {
    if (this.currentEntry.isValid()) {
      this.dataTransformation.entries[this.currentIndex] = this.currentEntry;
      this.refreshEntries();
    }
  }

  removeEntry(event: Event, index: number) {
    event.preventDefault();
    event.stopImmediatePropagation();
    this.dataTransformation.entries.splice(index, 1);
    this.refreshEntries();
  }

  getNameType(entry: PropertyTransformation) {
    switch (entry.newPath) {
      case PropertyName.DATA_ID:
        return 'fingerprint';
      case PropertyName.DEVICE_ID:
        return 'sensors';
      case PropertyName.DEVICE_NAME:
        return 'badge';
      case PropertyName.REPORTED_AT:
        return 'schedule';
      case PropertyName.LATITUDE:
        return 'share_location';
      case PropertyName.LONGITUDE:
        return 'share_location';
      case PropertyName.MOTION:
        return 'gesture';
      case PropertyName.VELOCITY:
        return 'speed';
      case PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER:
        return 'opacity';
      case PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE:
        return 'opacity';
      case PropertyName.AIR_PRESSURE:
        return 'compress';
      case PropertyName.TEMPERATURE:
        return 'thermostat';
      case PropertyName.AQI:
        return 'air';
      case PropertyName.ALTITUDE:
        return 'terrain';
      case PropertyName.SOIL_MOISTURE:
        return 'grass';
      case PropertyName.ILLUMINANCE:
        return 'wb_sunny';
      case PropertyName.BATTERY_PERCENTAGE:
        return 'battery_3_bar';
      case PropertyName.BATTERY_VOLTS:
        return 'battery_3_bar';
      case PropertyName.TRIGGER:
        return 'warning';
      case PropertyName.BATTERY_MAX_VOLTS:
        return 'battery_full';
      case PropertyName.BATTERY_MIN_VOLTS:
        return 'battery_0_bar';
      case PropertyName.DISTANCE:
        return 'straighten';
      case PropertyName.MAX_DISTANCE:
        return 'straighten';
      case PropertyName.MIN_DISTANCE:
        return 'straighten';
      case PropertyName.SOIL_CONDUCTIVITY:
        return 'electric_bolt';
      case PropertyName.CO2:
        return 'co2';
      case PropertyName.WATER_PRESSURE:
        return 'water';
      case PropertyName.OCCUPATION:
        return 'fullscreen';
      case PropertyName.VOC:
        return 'volcano';
      case PropertyName.PM2_5:
        return 'volcano';
      case PropertyName.PM10:
        return 'volcano';
      case PropertyName.O3:
        return 'masks';
      case PropertyName.NO2:
        return 'masks';
      case PropertyName.NH3:
        return 'masks';
      case PropertyName.CO:
        return 'masks';
      case PropertyName.PH:
        return 'masks';
      case PropertyName.INVALID:
        return 'error';
    }
  }

  isGlobal(type: PropertyName): boolean {
    return type === PropertyName.INVALID ||
      type === PropertyName.DATA_ID ||
      type === PropertyName.DEVICE_NAME ||
      type === PropertyName.DEVICE_ID ||
      type === PropertyName.REPORTED_AT ||
      type === PropertyName.CO2
  }

  editEntry(index: number) {
    if (
      this.dataTransformationView != this.dataTransformationViewType.Compare
    ) {
      this.currentIndex = index;
      this.currentEntry = this.dataTransformation.entries[index];
      this.entryView = EntryViewType.Edit;
    }
  }

  private resetView() {
    this.dataTransformationView = this.dataTransformationViewType.New;
    this.dataTransformation = DataTransformation.empty();
    this.resetOptions();
  }

  private refreshEntries() {
    this.resetOptions();
    this.entryView = EntryViewType.List;
    this.clearEntryFields();
  }

  private resetOptions() {
    const usedPropertyNameTypes = this.dataTransformation.entries.map((e) =>
      e.newPath.toString()
    );
    this.propertyNameTypes = Object.values(PropertyName).filter(
      (v) => !usedPropertyNameTypes.includes(v)
    );
  }

  private clearEntryFields() {
    this.currentEntry = PropertyTransformation.empty();
  }
}
