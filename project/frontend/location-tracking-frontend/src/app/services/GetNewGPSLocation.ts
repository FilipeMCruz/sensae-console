import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {SensorDTO} from "../dtos/SensorDTO";

@Injectable({
  providedIn: 'root'
})
export class GetNewGPSLocation {
  private query: any;

  constructor(private apollo: Apollo) {
  }

  getData(id: string): Observable<FetchResult<SensorDTO>> {
    this.query = gql`
      subscription location($deviceId: ID!) {
        location(deviceId: $deviceId) {
          dataId
          reportedAt
          deviceId
          data {
            longitude
            latitude
          }
        }
      }
    `;

    return this.apollo.subscribe<SensorDTO>({query: this.query, variables: {id}});
  }
}
