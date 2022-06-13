import {DeliveryType} from "./DeliveryType";
import {ContentType} from "../notification/ContentType";

export class AddresseeConfigurationEntry {
  constructor(public deliveryType: DeliveryType, public contentType: ContentType, public mute: boolean) {
  }
}
