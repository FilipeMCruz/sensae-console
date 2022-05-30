import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {
  FetchConfiguration,
  FetchNotificationHistory,
  SubscribeToNotification
} from "@frontend-services/notification-management/services";
import {Notification, NotificationHistoryQuery} from "@frontend-services/notification-management/model";
import {Sort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Subscription} from "rxjs";
import {NotificationService} from "@frontend-services/mutual";
import {filter} from "rxjs/operators";

@Component({
  selector: 'frontend-services-notification-management-page',
  templateUrl: './notification-management-page.component.html',
  styleUrls: ['./notification-management-page.component.scss'],
})
export class NotificationManagementPageComponent implements OnInit, OnDestroy {

  loading = true;

  data: Array<Notification> = [];

  displayedColumns = ['category', 'subCategory', 'severity', 'reportedAt'];

  dataSource = new MatTableDataSource(this.data);

  private subscription!: Subscription;

  constructor(
    public dialog: MatDialog,
    private collector: FetchNotificationHistory,
    private notificationEmitter: NotificationService,
    private configurationReader: FetchConfiguration
  ) {
  }

  ngOnInit(): void {
    this.fetchLastMonthNotifications();
    this.notificationEmitter.start();
    this.subscription = this.notificationEmitter
      .getCurrentData()
      .pipe(filter(next => !next.isEmpty()))
      .subscribe((next: Notification) => this.dataSource.data.push(next));
  }

  fetchLastMonthNotifications() {
    this.loading = true;
    this.collector
      .getData(NotificationHistoryQuery.lastMonth())
      .subscribe(
        data => this.dataSource.data = data,
        error => error,
        () => this.loading = false);
  }

  sortData(sort: Sort) {
    const data = this.dataSource.data.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSource.data = data;
      return;
    }

    this.dataSource.data = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'category':
          return this.compare(a.contentType.category, b.contentType.category, isAsc);
        case 'subCategory':
          return this.compare(a.contentType.subCategory, b.contentType.subCategory, isAsc);
        case 'reportedAt':
          return this.compare(a.reportedAt.getTime(), b.reportedAt.getTime(), isAsc);
        case 'severity':
          return this.compare(a.contentType.getSeverityAsNumber(), b.contentType.getSeverityAsNumber(), isAsc);
        default:
          return 0;
      }
    });
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
