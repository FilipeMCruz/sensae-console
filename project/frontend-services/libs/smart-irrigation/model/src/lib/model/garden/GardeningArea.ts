import {AreaBoundary} from "./AreaBoundary";
import {GardeningAreaName} from "./GardeningAreaName";
import {GardeningAreaId} from "./GardeningAreaId";
import center from "@turf/center";
import {Feature} from "geojson";
import * as mapboxgl from "mapbox-gl";

export class GardeningArea {
  constructor(public id: GardeningAreaId, public name: GardeningAreaName, public area: Array<AreaBoundary>) {
  }

  static getLabelStyle(): mapboxgl.AnyLayer {
    return {
      'id': 'gardens-labels',
      'type': 'symbol',
      'source': 'gardens',
      'layout': {
        'text-field': ['get', 'description'],
        'text-variable-anchor': ['top', 'bottom', 'left', 'right'],
        'text-radial-offset': 0.5,
        'text-justify': 'auto',
        'icon-image': "garden-15"
      }
    }
  }

  static getAreaStyle(): mapboxgl.AnyLayer {
    return {
      'id': 'gardens',
      'type': 'fill',
      'source': 'gardens',
      'layout': {},
      'paint': {
        'fill-color': '#a4ac86',
        'fill-opacity': 0.5
      }
    }
  }

  static getBoundaryStyle(): mapboxgl.AnyLayer {
    return {
      'id': 'gardens-outline',
      'type': 'line',
      'source': 'gardens',
      'layout': {},
      'paint': {
        'line-color': '#582f0e',
        'line-width': 2
      }
    }
  }

  center(): number[] {
    const feature = {
      type: 'MultiPoint',
      coordinates: this.getArea(),
    };

    return center(feature).geometry.coordinates;
  }

  asFeature(): Feature {
    return {
      type: 'Feature',
      geometry: {
        type: 'Polygon',
        coordinates: [
          this.getArea()
        ]
      },
      properties: {
        description: this.name.value,
      }
    };
  }

  getArea() {
    const cords = [];
    for (let i = 0; i < this.area.length; i++) {
      const boundary = this.area.find(b => b.position == i);
      if (boundary)
        cords.push(boundary.point.toCoordinates())
    }
    const boundary = this.area.find(b => b.position == 0);
    if (boundary)
      cords.push(boundary.point.toCoordinates())
    return cords;
  }
}
