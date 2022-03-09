import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {EMPTY, Observable} from "rxjs";
import {FilteredSensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";
import {HttpHeaders} from "@angular/common/http";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByDevice {

  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(devices: Array<string>): Observable<DeviceData> {
    if (!this.auth.isAuthenticated()) return EMPTY;

    const query = gql`
      subscription location($devices: [String]){
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

    return this.apollo.use("fleetManagement").subscribe<FilteredSensorDTO>({
      query,
      variables: {devices},
      context: {headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken())},
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: FilteredSensorDTO) => DeviceLiveDataMapper.dtoToModel(data.location))
    );
  }
}
