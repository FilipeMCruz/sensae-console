import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DataTransformationDialogComponent} from '../data-transformation-dialog/data-transformation-dialog.component';
import {
  DataTransformation,
  DataTransformationPair,
  DataTransformationViewType,
  SensorTypeId,
} from '@frontend-services/data-processor/model';
import {
  DeleteDataTransformation,
  GetAllDataTransformations,
  IndexDataTransformation,
} from '@frontend-services/data-processor/services';
import {AuthService} from "@frontend-services/simple-auth-lib";
import {DateFormat} from "@frontend-services/core";

const DAY = 86400;

const HOUR = 3600;

const TEN_MINUTES = 600;

@Component({
  selector: 'frontend-services-data-transformations-page',
  templateUrl: './data-transformations-page.component.html',
  styleUrls: ['./data-transformations-page.component.scss'],
})
export class DataTransformationsPageComponent implements OnInit {
  dataTransformations: Array<DataTransformation> =
    new Array<DataTransformation>();

  dataTransformationViewType = DataTransformationViewType;
  loading = true;

  constructor(
    public dialog: MatDialog,
    private collector: GetAllDataTransformations,
    private indexer: IndexDataTransformation,
    private eraser: DeleteDataTransformation,
    private authService: AuthService
  ) {
  }

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
    this.loading = true;
    this.collector
      .getData()
      .subscribe(
        (data: Array<DataTransformation>) => (this.dataTransformations = data.sort((a, b) => a.data.type.localeCompare(b.data.type))),
        error => error,
        () => this.loading = false);
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
    this.indexer
      .index(event)
      .subscribe((dataTransformation: DataTransformation) => {
        this.dataTransformations = this.dataTransformations.filter(
          (r) => r.data.type != dataTransformation.data.type
        );
        this.dataTransformations.push(dataTransformation);
      });
  }

  deleteItem(event: DataTransformation) {
    this.eraser.delete(event).subscribe((data: SensorTypeId) => {
      this.dataTransformations = this.dataTransformations.filter(
        (r) => r.data.type != data.type
      );
    });
  }

  canEdit() {
    return this.authService.isAllowed(Array.of("data_transformations:transformations:edit"));
  }

  canDelete() {
    return this.authService.isAllowed(Array.of("data_transformations:transformations:delete"));
  }

  getLastTimeSeen(decoder: DataTransformation) {
    const seconds = Math.floor((new Date().valueOf() - decoder.lastTimeSeen.valueOf()) / 1000);

    const interval = seconds / 31536000;
    if (interval > 1) {
      return "never";
    }

    return DateFormat.timeAgo(decoder.lastTimeSeen) + " ago";
  }

  getLastTimeSeenColor(transformation: DataTransformation) {
    const seconds = Math.floor((new Date().valueOf() - transformation.lastTimeSeen.valueOf()) / 1000);
    if (seconds >= DAY) return "red-chip";
    else if (seconds >= HOUR) return "orange-chip";
    else if (seconds >= TEN_MINUTES) return "yellow-chip";
    else return "green-chip";
  }
}
