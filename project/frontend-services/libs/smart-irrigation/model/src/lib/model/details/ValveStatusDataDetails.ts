export enum ValveStatusDataDetailsType {
  OPEN,
  CLOSED
}

export class ValveStatusDataDetails {
  constructor(public status: ValveStatusDataDetailsType) {
  }
}
