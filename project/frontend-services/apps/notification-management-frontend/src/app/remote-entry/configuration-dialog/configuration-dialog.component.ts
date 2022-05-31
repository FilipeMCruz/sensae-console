import {AfterViewInit, Component, Inject, ViewChild} from '@angular/core';
import {FetchConfiguration, UpdateAddresseeConfiguration} from "@frontend-services/notification-management/services";
import {
  AddresseeConfiguration,
  AddresseeConfigurationEntry, AddresseeConfigurationTableView,
  ContentType,
  DeliveryType, NotificationSeverityLevel
} from "@frontend-services/notification-management/model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatTable} from "@angular/material/table";

@Component({
  selector: 'frontend-services-configuration-dialog',
  templateUrl: 'configuration-dialog.component.html',
  styleUrls: ['./configuration-dialog.component.scss'],
})
export class ConfigurationDialogComponent implements AfterViewInit {

  displayedColumns = ['category', 'subCategory', 'severity', 'ui', 'email', 'sms', 'actions'];

  configuration: AddresseeConfiguration = AddresseeConfiguration.empty();


  @ViewChild(MatTable) table!: MatTable<AddresseeConfigurationTableView>;

  dataSource: Array<AddresseeConfigurationTableView> = [];
  newCategory = "";
  newSubCategory = "";
  newSeverity = "INFORMATION";

  constructor(
    private configurationReader: FetchConfiguration,
    public dialogRef: MatDialogRef<ConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<ContentType>,
    private mutateConfiguration: UpdateAddresseeConfiguration) {
  }

  onNoClick(): void {
    AddresseeConfiguration.fromTableView(this.dataSource);
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
      this.dataSource = this.configuration.toTableView().sort((n1, n2) => {
        return n1.contentType.uniqueKey().localeCompare(n2.contentType.uniqueKey())
      });
      this.table.renderRows();
    });
  }

  save() {
    const config = AddresseeConfiguration.fromTableView(this.dataSource);
    this.mutateConfiguration.execute(config).subscribe();
    this.dialogRef.close();
  }

  toCamelCase(value: string) {
    const result = value.trim().replace(/ /g, "");
    return result.charAt(0).toLowerCase() + result.slice(1);
  }

  addEntry() {
    this.dataSource.push(this.buildEntry());
    this.dataSource = this.dataSource.sort((n1, n2) => {
      return n1.contentType.uniqueKey().localeCompare(n2.contentType.uniqueKey())
    });
    this.table.renderRows();
  }

  private buildEntry(): AddresseeConfigurationTableView {
    let severity: NotificationSeverityLevel;
    switch (this.newSeverity) {
      case "WARNING": {
        severity = NotificationSeverityLevel.WARNING;
        break;
      }
      case "WATCH": {
        severity = NotificationSeverityLevel.WATCH;
        break;
      }
      case "CRITICAL": {
        severity = NotificationSeverityLevel.CRITICAL;
        break;
      }
      case "ADVISORY": {
        severity = NotificationSeverityLevel.ADVISORY;
        break;
      }
      default: {
        severity = NotificationSeverityLevel.INFORMATION;
        break;
      }
    }
    const contentType = new ContentType(this.toCamelCase(this.newCategory), this.toCamelCase(this.newSubCategory), severity);
    return new AddresseeConfigurationTableView(contentType, false, false, false);
  }

  validEntry() {
    let outcome = this.newCategory.trim().length !== 0 && this.newSubCategory.trim().length !== 0;

    if (outcome) {
      const entry = this.buildEntry();
      outcome = !this.dataSource.some(e => e.contentType.equals(entry.contentType))
    }

    return outcome;
  }

  getCategoryOptions() {
    return this.dataSource.map(e => e.contentType.getCategory());
  }

  getSubCategoryOptions() {
    return this.dataSource.map(e => e.contentType.getSubCategory());
  }

  removeEntry(elem: AddresseeConfigurationTableView) {
    this.dataSource = this.dataSource.filter(e => !e.contentType.equals(elem.contentType)).sort((n1, n2) => {
      return n1.contentType.uniqueKey().localeCompare(n2.contentType.uniqueKey())
    });
    this.table.renderRows();
  }
}
