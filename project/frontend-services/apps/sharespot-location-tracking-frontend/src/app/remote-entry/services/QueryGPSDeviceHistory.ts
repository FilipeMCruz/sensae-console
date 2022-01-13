import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {GPSSensorDataQuery, HistorySensorDTO} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class QueryGPSDeviceHistory {

  constructor(private apollo: Apollo) {
  }

  getData(filters: GPSSensorDataQuery): Observable<FetchResult<HistorySensorDTO>> {
    const query = gql`
      query history($filters: GPSSensorDataQuery){
        history(filters: $filters){
          deviceId
          deviceName
          startTime
          endTime
          data{
            longitude
            latitude
          }
        }
      }
    `;

    return this.apollo.use("locationTracking").subscribe<HistorySensorDTO>({query, variables: {filters}});
  }
}
