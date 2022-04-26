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

  getNameType(entry: PropertyTransformation): string {
    return entry.getIcon();
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
      this.currentEntry = this.dataTransformation.entries[index].clone();
      this.entryView = EntryViewType.Edit;
    }
  }

  private resetView() {
    this.dataTransformationView = this.dataTransformationViewType.New;
    this.dataTransformation = DataTransformation.empty();
  }

  private refreshEntries() {
    this.entryView = EntryViewType.List;
    this.clearEntryFields();
  }

  private clearEntryFields() {
    this.currentEntry = PropertyTransformation.empty();
  }
}
