import {ContentType} from "../notification/ContentType";

export class AddresseeConfigurationTableView {
  constructor(public contentType: ContentType, public sms: boolean, public email: boolean, public notification: boolean) {
  }
}
