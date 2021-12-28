import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DataTransformationPair} from "../../model/DataTransformationPair";
import {DataTransformationViewType} from "../../model/DataTransformationViewType";

@Component({
  selector: 'frontend-services-data-transformation-dialog',
  templateUrl: 'data-transformation-dialog.component.html',
  styleUrls: ['./data-transformation-dialog.component.scss']
})
export class DataTransformationDialogComponent {

  dataTransformationViewType = DataTransformationViewType;

  constructor(
    public dialogRef: MatDialogRef<DataTransformationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DataTransformationPair,
  ) {
  }
}