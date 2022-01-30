import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {GPSSensorDataQuery, HistorySensorDTO} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceHistory} from "../model/pastdata/DeviceHistory";
import {DevicePastDataMapper} from "../mappers/DevicePastDataMapper";

@Injectable({
  providedIn: 'root'
})
export class QueryGPSDeviceHistory {

  constructor(private apollo: Apollo) {
  }

  getData(filters: GPSSensorDataQuery): Observable<Array<DeviceHistory>> {
    const query = gql`
      query history($filters: GPSSensorDataQuery){
        history(filters: $filters){
          deviceId
          deviceName
          startTime
          endTime
          distance
          segments {
            type
            steps {
              status {
                motion
              }
              reportedAt
              gps {
                longitude
                latitude
              }
            }
          }
        }
      }
    `;

    return this.apollo.use("locationTracking").subscribe<HistorySensorDTO>({query, variables: {filters}})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: HistorySensorDTO) => DevicePastDataMapper.dtoToModel(data))
      );
  }
}
