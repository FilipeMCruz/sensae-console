import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {
  FetchConfiguration,
  FetchNotificationHistory, UpdateAddresseeConfiguration,
} from "@frontend-services/notification-management/services";
import {
  AddresseeConfiguration,
  Notification,
  NotificationHistoryQuery
} from "@frontend-services/notification-management/model";
import {Sort} from "@angular/material/sort";
import {Subscription} from "rxjs";
import {NotificationService} from "@frontend-services/mutual";
import {filter} from "rxjs/operators";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ConfigurationDialogComponent} from "../configuration-dialog/configuration-dialog.component";

@Component({
  selector: 'frontend-services-notification-management-page',
  templateUrl: './notification-management-page.component.html',
  styleUrls: ['./notification-management-page.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed, void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      transition('expanded <=> void', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class NotificationManagementPageComponent implements OnInit, OnDestroy {

  loading = true;

  sortedData: Array<Notification> = [];

  displayedColumns = ['category', 'subCategory', 'severity', 'reportedAt'];

  expandedElement: Notification | undefined;

  private subscription!: Subscription;
  private sort: Sort = {active: 'reportedAt', direction: 'desc'};

  constructor(
    public dialog: MatDialog,
    private collector: FetchNotificationHistory,
    private notificationEmitter: NotificationService,
  ) {
  }

  ngOnInit(): void {
    this.fetchLastMonthNotifications();
    this.notificationEmitter.start();
    this.subscription = this.notificationEmitter
      .getCurrentData()
      .pipe(filter(next => !next.isEmpty()))
      .subscribe((next: Notification) => {
        this.sortedData.push(next)
        this.sortData(this.sort);
      });
  }

  fetchLastMonthNotifications() {
    this.loading = true;
    this.collector
      .getData(NotificationHistoryQuery.lastMonth())
      .subscribe(
        data => {
          this.sortedData = data
          this.sortData(this.sort);
        },
        error => error,
        () => this.loading = false);
  }

  sortData(sort: Sort) {
    this.sort = sort;
    const data = this.sortedData.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
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

  openConfiguration() {
    this.dialog.open(ConfigurationDialogComponent, {
      width: '70%',
      height: '60%',
      data: this.sortedData.map(s => s.contentType).filter(({category, subCategory, severity}, index, a) =>
        a.findIndex(e => category === e.category &&
          severity === e.severity &&
          subCategory === e.subCategory) === index)
    });
  }
}
