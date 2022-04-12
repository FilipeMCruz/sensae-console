export enum ValveStatusDataDetailsType {
  OPEN,
  CLOSE
}

export class ValveStatusDataDetails {
  constructor(public status: ValveStatusDataDetails) {
  }
}
