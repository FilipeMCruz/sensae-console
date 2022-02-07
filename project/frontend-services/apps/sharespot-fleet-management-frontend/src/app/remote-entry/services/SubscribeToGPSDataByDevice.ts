import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FilteredSensorDTO, SensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByDevice {

  constructor(private apollo: Apollo) {
  }

  getData(devices: Array<string>): Observable<DeviceData> {
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

    return this.apollo.use("fleetManagement").subscribe<FilteredSensorDTO>({query, variables: {devices}})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: FilteredSensorDTO) => DeviceLiveDataMapper.dtoToModel(data.location))
      );
  }
}
