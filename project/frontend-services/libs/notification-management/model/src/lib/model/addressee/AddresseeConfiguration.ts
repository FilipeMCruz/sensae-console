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
      normalView.push(new AddresseeConfigurationEntry(DeliveryType.UI, row.contentType, !row.history))
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
      const history = group.find(g => g.deliveryType === DeliveryType.UI);

      const historyValue = history !== undefined ? !history.mute : false;
      const notificationValue = notification !== undefined ? !notification.mute : false;
      const emailValue = email !== undefined ? !email.mute : false;
      const smsValue = sms !== undefined ? !sms.mute : false;

      tableView.push(new AddresseeConfigurationTableView(group[0].contentType, smsValue, emailValue, notificationValue, historyValue));
    }
    return tableView;
  }
}
