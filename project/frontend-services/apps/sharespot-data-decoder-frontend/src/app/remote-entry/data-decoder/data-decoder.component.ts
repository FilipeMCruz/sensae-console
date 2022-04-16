import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {PropertyTransformation,} from '@frontend-services/data-processor/model';
import {DataDecoder, DataDecoderViewType} from "@frontend-services/data-decoder/model";

@Component({
  selector: 'frontend-services-data-decoder',
  templateUrl: './data-decoder.component.html',
  styleUrls: ['./data-decoder.component.scss'],
})
export class DataDecoderComponent implements OnChanges {
  @Input() entry!: DataDecoder;
  @Input() dataDecoderViewEntry!: DataDecoderViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;

  @Output() newDataDecoderEvent = new EventEmitter<DataDecoder>();
  @Output() deleteDataDecoderEvent =
    new EventEmitter<DataDecoder>();

  dataDecoder = DataDecoder.empty();
  currentEntry = PropertyTransformation.empty();

  dataDecoderView = DataDecoderViewType.New;
  dataDecoderViewType = DataDecoderViewType;

  ngOnChanges(): void {
    if (this.entry) {
      this.dataDecoderView = DataDecoderViewType.Edit;
      this.dataDecoder = this.entry;
    }
    if (this.dataDecoderViewEntry) {
      this.dataDecoderView = this.dataDecoderViewEntry;
    }
  }

  saveDataDecoder() {
    this.newDataDecoderEvent.emit(this.dataDecoder);
    this.resetView();
  }

  deleteDataDecoder() {
    this.deleteDataDecoderEvent.emit(this.dataDecoder);
    this.resetView();
  }

  private resetView() {
    this.dataDecoderView = this.dataDecoderViewType.New;
    this.dataDecoder = DataDecoder.empty();
  }
}
