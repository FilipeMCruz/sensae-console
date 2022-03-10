import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDataTransformation } from '../../services/DeleteDataTransformation';
import { GetAllDataTransformations } from '../../services/GetAllDataTransformations';
import { IndexDataTransformation } from '../../services/IndexDataTransformation';
import { DataTransformationPair } from '../../model/DataTransformationPair';
import { DataTransformation } from '../../model/DataTransformation';
import { DataTransformationViewType } from '../../model/DataTransformationViewType';
import { DataTransformationDialogComponent } from '../data-transformation-dialog/data-transformation-dialog.component';

@Component({
  selector: 'frontend-services-data-transformations-page',
  templateUrl: './data-transformations-page.component.html',
  styleUrls: ['./data-transformations-page.component.scss'],
})
export class DataTransformationsPageComponent implements OnInit {
  dataTransformations: Array<DataTransformation> =
    new Array<DataTransformation>();

  dataTransformationViewType = DataTransformationViewType;

  constructor(
    public dialog: MatDialog,
    private collector: GetAllDataTransformations,
    private indexer: IndexDataTransformation,
    private eraser: DeleteDataTransformation
  ) {}

  openDialog(data: DataTransformationPair) {
    const dialogRef = this.dialog.open(DataTransformationDialogComponent, {
      width: '1400px',
      data,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.saveItem(data.fresh);
      }
    });
  }

  ngOnInit(): void {
    this.fetchAllDataTransformations();
  }

  fetchAllDataTransformations() {
    this.collector
      .getData()
      .subscribe((data) => (this.dataTransformations = data));
  }

  updateItem(event: DataTransformation) {
    this.saveItem(event);
  }

  addItem(event: DataTransformation) {
    const dataTransformations = this.dataTransformations.filter(
      (r) => r.data.type == event.data.type
    );
    if (dataTransformations.length != 0) {
      this.openDialog(
        new DataTransformationPair(event, dataTransformations[0])
      );
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DataTransformation) {
    this.indexer.index(event).subscribe((dataTransformation) => {
      this.dataTransformations = this.dataTransformations.filter(
        (r) => r.data.type != dataTransformation.data.type
      );
      this.dataTransformations.push(dataTransformation);
    });
  }

  deleteItem(event: DataTransformation) {
    this.eraser.delete(event).subscribe((data) => {
      this.dataTransformations = this.dataTransformations.filter(
        (r) => r.data.type != data.type
      );
    });
  }
}
