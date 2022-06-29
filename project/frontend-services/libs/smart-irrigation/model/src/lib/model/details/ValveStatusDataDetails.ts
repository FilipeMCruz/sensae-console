export enum ValveStatusDataDetailsType {
  OPEN = "  Open",
  CLOSED = "Closed"
}

export class ValveStatusDataDetails {
  constructor(public status: ValveStatusDataDetailsType) {
  }
}
