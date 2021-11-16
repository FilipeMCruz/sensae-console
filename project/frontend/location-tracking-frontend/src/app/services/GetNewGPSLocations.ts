import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {SensorDTO} from "../dtos/SensorDTO";

@Injectable({
  providedIn: 'root'
})
export class GetNewGPSLocations {
  private query: any;

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<FetchResult<SensorDTO>> {
    this.query = gql`
      subscription {
        locations {
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

    return this.apollo.subscribe<SensorDTO>({query: this.query});
  }
}
