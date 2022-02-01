export class DeviceCoordinates {
  constructor(public latitude: number, public longitude: number) {
  }

  toCoordinates(): number[] {
    return [this.longitude, this.latitude];
  }
}
