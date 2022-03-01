import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {EntryViewType} from "../../model/EntryViewType";
import {DataTransformation} from "../../model/DataTransformation";
import {PropertyTransformation} from "../../model/PropertyTransformation";
import {DataTransformationViewType} from "../../model/DataTransformationViewType";
import {PropertyName} from "../../model/PropertyName";

@Component({
  selector: 'frontend-services-data-transformation',
  templateUrl: './data-transformation.component.html',
  styleUrls: ['./data-transformation.component.scss']
})
export class DataTransformationComponent implements OnChanges {

  @Input() entry!: DataTransformation;
  @Input() dataTransformationViewEntry!: DataTransformationViewType;

  @Output() newDataTransformationEvent = new EventEmitter<DataTransformation>();
  @Output() deleteDataTransformationEvent = new EventEmitter<DataTransformation>();

  dataTransformation = DataTransformation.empty();
  currentEntry = PropertyTransformation.empty();

  dataTransformationView = DataTransformationViewType.New;
  dataTransformationViewType = DataTransformationViewType;

  entryView = EntryViewType.List;
  entryViewType = EntryViewType;

  nameType = PropertyName;

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
        return "fingerprint";
      case PropertyName.DEVICE_ID:
        return "sensors";
      case PropertyName.DEVICE_NAME:
        return "badge";
      case PropertyName.REPORTED_AT:
        return "schedule";
      case PropertyName.LATITUDE:
        return "share_location";
      case PropertyName.LONGITUDE:
        return "share_location";
      case PropertyName.MOTION:
        return "gesture";
      case PropertyName.VELOCITY:
        return "speed";
      case PropertyName.HUMIDITY:
        return "opacity";
      case PropertyName.PRESSURE:
        return "compress";
      case PropertyName.TEMPERATURE:
        return "thermostat";
      case PropertyName.AQI:
        return "air";
      case PropertyName.INVALID:
        return "error";
    }
  }

  editEntry(index: number) {
    if (this.dataTransformationView != this.dataTransformationViewType.Compare) {
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
    const usedPropertyNameTypes = this.dataTransformation.entries.map(e => e.newPath.toString());
    this.propertyNameTypes = Object.values(PropertyName).filter(v => !usedPropertyNameTypes.includes(v));
  }

  private clearEntryFields() {
    this.currentEntry = PropertyTransformation.empty();
  }
}
