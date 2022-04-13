import {Position} from "geojson";

export class GPSDataDetails {
  constructor(public latitude: number, public longitude: number, public altitude: number) {
  }

  static fromCoordinates(coordinates: Position) {
    return new GPSDataDetails(coordinates[1], coordinates[0], 0);
  }

  toCoordinates(): Position {
    return [+this.longitude, +this.latitude] as Position;
  }
}
