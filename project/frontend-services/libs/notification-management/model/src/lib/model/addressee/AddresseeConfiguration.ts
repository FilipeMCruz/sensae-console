import {AddresseeConfigurationEntry} from "./AddresseeConfigurationEntry";
import {ContentType} from "../notification/ContentType";
import {AddresseeConfigurationTableView} from "./AddresseeConfigurationTableView";
import {groupBy} from "@frontend-services/core";
import {DeliveryType} from "./DeliveryType";

export class AddresseeConfiguration {
  constructor(public entries: Array<AddresseeConfigurationEntry>) {
  }

  static fromTableView(dataSource: Array<AddresseeConfigurationTableView>): AddresseeConfiguration {
    const normalView: Array<AddresseeConfigurationEntry> = [];
    dataSource.forEach(row => {
      normalView.push(new AddresseeConfigurationEntry(DeliveryType.SMS, row.contentType, !row.sms))
      normalView.push(new AddresseeConfigurationEntry(DeliveryType.EMAIL, row.contentType, !row.email))
      normalView.push(new AddresseeConfigurationEntry(DeliveryType.NOTIFICATION, row.contentType, !row.notification))
    });
    return new AddresseeConfiguration(normalView);
  }

  static empty() {
    return new AddresseeConfiguration([]);
  }

  public add(entry: AddresseeConfigurationEntry) {
    this.entries.push(entry);
  }

  contains(data: ContentType) {
    return this.entries.some(d => d.contentType.equals(data));
  }

  toTableView(): Array<AddresseeConfigurationTableView> {
    const tableView = [];
    const groups = groupBy(this.entries, entry => entry.contentType.uniqueKey(), () => "none");
    for (const groupsKey in groups) {
      const group = groups[groupsKey];
      const notification = group.find(g => g.deliveryType === DeliveryType.NOTIFICATION);
      const email = group.find(g => g.deliveryType === DeliveryType.EMAIL);
      const sms = group.find(g => g.deliveryType === DeliveryType.SMS);

      let smsValue = false;
      if (sms !== undefined) {
        smsValue = !sms.mute;
      }
      let emailValue = false;
      if (email !== undefined) {
        emailValue = !email.mute;
      }
      let notificationValue = false;
      if (notification !== undefined) {
        notificationValue = !notification.mute;
      }

      const contentType = group[0].contentType;

      tableView.push(new AddresseeConfigurationTableView(contentType, smsValue, emailValue, notificationValue));
    }
    return tableView;
  }
}
