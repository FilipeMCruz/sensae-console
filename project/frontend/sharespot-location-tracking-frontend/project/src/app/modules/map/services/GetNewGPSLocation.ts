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

  getData(deviceId: string): Observable<FetchResult<SensorDTO>> {
    this.query = gql`
      subscription location($deviceId: String){
        location(deviceId: $deviceId){
          dataId
          device{
            id
            name
          }
          reportedAt
          data{
            gps{
              longitude
              latitude
            }
          }
          record{
            label
            content
          }
        }
      }
    `;

    return this.apollo.subscribe<SensorDTO>({query: this.query, variables: {deviceId}});
  }
}
