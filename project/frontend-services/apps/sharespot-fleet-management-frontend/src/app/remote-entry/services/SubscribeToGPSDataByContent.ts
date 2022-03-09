import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {EMPTY, Observable} from "rxjs";
import {FilteredByContentSensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";
import {extract, isNonNull} from "./ObservableFunctions";
import {HttpHeaders} from "@angular/common/http";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByContent {

  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(content: string): Observable<DeviceData> {
    if (!this.auth.isAuthenticated()) return EMPTY;

    const query = gql`
      subscription locationByContent($content: String){
        locationByContent(content: $content){
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

    return this.apollo.use("fleetManagement").subscribe<FilteredByContentSensorDTO>({
      query,
      context: {headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken())},
      variables: {content}
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: FilteredByContentSensorDTO) => DeviceLiveDataMapper.dtoToModel(data.locationByContent))
    );
  }
}
