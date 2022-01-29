import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {FilteredByDeviceGPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class QueryLatestGPSSpecificDeviceData {

  constructor(private apollo: Apollo) {
  }

  getData(devices: Array<string>): Observable<FetchResult<FilteredByDeviceGPSSensorLatestData>> {
    const query = gql`
      query latestByDevice($devices: [String]){
        latestByDevice(devices: $devices){
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

    return this.apollo.use("locationTracking").subscribe<FilteredByDeviceGPSSensorLatestData>({
      query,
      variables: {devices}
    });
  }
}
