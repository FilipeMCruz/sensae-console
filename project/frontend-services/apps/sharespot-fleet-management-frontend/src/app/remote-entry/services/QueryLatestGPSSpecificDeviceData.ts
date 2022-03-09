import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FilteredByDeviceGPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";
import {HttpHeaders} from "@angular/common/http";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({
  providedIn: 'root'
})
export class QueryLatestGPSSpecificDeviceData {

  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(devices: Array<string>): Observable<DeviceData[]> {
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

    return this.apollo.use("fleetManagement").subscribe<FilteredByDeviceGPSSensorLatestData>({
      query,
      context: {headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken())},
      variables: {devices}
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: FilteredByDeviceGPSSensorLatestData) => data.latestByDevice.map(s => DeviceLiveDataMapper.dtoToModel(s)))
    );
  }
}
