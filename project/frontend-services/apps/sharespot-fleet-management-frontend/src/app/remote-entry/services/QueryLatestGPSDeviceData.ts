import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {GPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
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

    return this.apollo.use("fleetManagement").subscribe<GPSSensorLatestData>({query})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: GPSSensorLatestData) => data.latest.map(s => DeviceLiveDataMapper.dtoToModel(s)))
      );
  }
}