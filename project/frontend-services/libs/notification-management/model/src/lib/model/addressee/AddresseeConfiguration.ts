import {AddresseeConfigurationEntry} from "./AddresseeConfigurationEntry";
import {ContentType} from "../notification/ContentType";

export class AddresseeConfiguration {
  constructor(public entries: Array<AddresseeConfigurationEntry>) {
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
}
