import {AddresseeConfigurationEntry} from "./AddresseeConfigurationEntry";

export class AddresseeConfiguration {
  constructor(public entries: Array<AddresseeConfigurationEntry>) {
  }

  static empty() {
    return new AddresseeConfiguration([]);
  }

  public add(entry: AddresseeConfigurationEntry) {
    this.entries.push(entry);
  }
}
