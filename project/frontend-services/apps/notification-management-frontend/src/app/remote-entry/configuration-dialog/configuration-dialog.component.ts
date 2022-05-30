import {AfterViewInit, Component, Inject} from '@angular/core';
import {FetchConfiguration, UpdateAddresseeConfiguration} from "@frontend-services/notification-management/services";
import {
  AddresseeConfiguration,
  AddresseeConfigurationEntry,
  ContentType,
  DeliveryType
} from "@frontend-services/notification-management/model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'frontend-services-configuration-dialog',
  templateUrl: 'configuration-dialog.component.html',
  styleUrls: ['./configuration-dialog.component.scss'],
})
export class ConfigurationDialogComponent implements AfterViewInit {

  displayedColumns = ['category', 'subCategory', 'severity', 'email', 'sms'];

  configuration: AddresseeConfiguration = AddresseeConfiguration.empty();

  constructor(
    private configurationReader: FetchConfiguration,
    public dialogRef: MatDialogRef<ConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<ContentType>,
    private mutateConfiguration: UpdateAddresseeConfiguration) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngAfterViewInit(): void {
    this.configurationReader.getData().subscribe(next => {
      this.data.forEach(content => {
        if (!next.contains(content)) {
          next.add(new AddresseeConfigurationEntry(DeliveryType.NOTIFICATION, content, false));
        }
      })
      this.configuration = next;
    });
  }

  sortData(sort: Sort) {
    const data = this.configuration.entries.slice();
    if (!sort.active || sort.direction === '') {
      this.configuration.entries = data;
      return;
    }

    this.configuration.entries = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'category':
          return this.compare(a.contentType.category, b.contentType.category, isAsc);
        case 'subCategory':
          return this.compare(a.contentType.subCategory, b.contentType.subCategory, isAsc);
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
}
