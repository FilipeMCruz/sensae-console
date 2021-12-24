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
    const nameType = entry.newPath;
    if (nameType === PropertyName.DEVICE_NAME) {
      return "badge";
    } else if (nameType === PropertyName.DATA_ID) {
      return "fingerprint";
    } else if (nameType === PropertyName.DEVICE_ID) {
      return "sensors";
    } else if (nameType === PropertyName.DEVICE_RECORDS) {
      return "info";
    } else if (nameType === PropertyName.LATITUDE) {
      return "share_location";
    } else if (nameType === PropertyName.LONGITUDE) {
      return "share_location";
    } else if (nameType === PropertyName.TEMPERATURE) {
      return "thermostat";
    } else if (nameType === PropertyName.REPORTED_AT) {
      return "schedule";
    }
    return "error";
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
