import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DataDecoderPair, DataDecoderViewType} from "@frontend-services/data-decoder/model";

@Component({
  selector: 'frontend-services-data-decoder-dialog',
  templateUrl: 'data-decoder-dialog.component.html',
  styleUrls: ['./data-decoder-dialog.component.scss'],
})
export class DataDecoderDialogComponent {
  dataDecoderViewType = DataDecoderViewType;

  constructor(
    public dialogRef: MatDialogRef<DataDecoderDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DataDecoderPair
  ) {
  }
}
