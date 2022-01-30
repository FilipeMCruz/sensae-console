import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {GPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {SensorMapper} from "../mappers/SensorMapper";
import {DeviceData} from "../model/livedata/DeviceData";

@Injectable({
  providedIn: 'root'
})
export class QueryLatestGPSDeviceData {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<DeviceData[]> {
    const query = gql`
      query latest{
        latest{
          dataId
          device{
            id
            name
            records{
              label
              content
            }
          }
          reportedAt
          data{
            gps{
              longitude
              latitude
            }
            status{
              motion
            }
          }
        }
      }
    `;

    return this.apollo.use("locationTracking").subscribe<GPSSensorLatestData>({query})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: GPSSensorLatestData) => data.latest.map(s => SensorMapper.dtoToModel(s)))
      );
  }
}
