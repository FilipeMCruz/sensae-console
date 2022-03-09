import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {EMPTY, Observable} from "rxjs";
import {SensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToAllGPSData {

  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<DeviceData> {
    if (!this.auth.isAuthenticated()) return EMPTY;

    const query = gql`
      subscription locations($Authorization: String){
        locations(Authorization: $Authorization){
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

    return this.apollo.use("fleetManagement").subscribe<SensorDTO>({
      query,
      variables: {Authorization: 'Bearer ' + this.auth.getToken()},
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: SensorDTO) => DeviceLiveDataMapper.dtoToModel(data.locations))
    );
  }
}
