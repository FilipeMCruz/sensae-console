import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {SensorDTO} from "../dtos/SensorDTO";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByDevice {

  constructor(private apollo: Apollo) {
  }

  getData(devices: Array<string>): Observable<FetchResult<SensorDTO>> {
    const query = gql`
      subscription location($devices: String){
        location(devices: $devices){
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

    return this.apollo.use("locationTracking").subscribe<SensorDTO>({query, variables: {devices}});
  }
}
