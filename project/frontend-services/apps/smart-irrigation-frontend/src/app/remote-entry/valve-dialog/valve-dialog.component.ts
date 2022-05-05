import {AfterViewInit, Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Data, ValveDataDetails} from "@frontend-services/smart-irrigation/model";

@Component({
  selector: 'frontend-services-valve-dialog',
  templateUrl: 'valve-dialog.component.html',
  styleUrls: ['./valve-dialog.component.scss'],
})
export class ValveDialogComponent implements AfterViewInit {

  status = "Closed";

  constructor(
    public dialogRef: MatDialogRef<ValveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Data,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngAfterViewInit(): void {
    const data1 = this.data.data as ValveDataDetails;
    this.status = data1.valve.status;
  }
}
