import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {SensorDTO} from "../dtos/SensorDTO";

@Injectable({
  providedIn: 'root'
})
export class GetNewGPSLocation {

  constructor(private apollo: Apollo) {
  }

  getData(device: string): Observable<FetchResult<SensorDTO>> {
    const query = gql`
      subscription location($device: String){
        location(device: $device){
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
          }
        }
      }
    `;

    return this.apollo.use("locationTracking").subscribe<SensorDTO>({query, variables: {device}});
  }
}
