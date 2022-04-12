export class GPSDataDetails {
  constructor(public latitude: number, public longitude: number, public altitude: number) {
  }

  toCoordinates(): number[] {
    return [this.longitude, this.latitude];
  }
}
