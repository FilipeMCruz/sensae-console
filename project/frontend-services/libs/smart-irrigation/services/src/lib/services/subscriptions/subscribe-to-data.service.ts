import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {Data} from "@frontend-services/smart-irrigation/model";
import {SubscribeToDataResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";
import {DataFilters} from "@frontend-services/smart-irrigation/model";

@Injectable({
  providedIn: 'root',
})
export class SubscribeToData {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["smart_irrigation:live_data:read"]);
  }

  getData(filters: DataFilters): Observable<Data> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      subscription data($filters: LiveDataFilter, $Authorization: String){
        data(filters: $filters, Authorization: $Authorization){
          dataId
          device{
            id
            name
            type
            remoteControl
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
              altitude
            }
            ...on ParkSensorDataDetails {
              soilMoisture {
                relativePercentage
              }
              illuminance {
                lux
              }
            }
            ...on StoveSensorDataDetails {
              temperature {
                celsius
              }
              humidity {
                relativePercentage
              }
            }
            ...on ValveDataDetails {
              valve {
                status
              }
            }
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<SubscribeToDataResultDTO>({
        query,
        variables: {
          filters: OperationsMapper.dataFiltersModelToDto(filters),
          Authorization: 'Bearer ' + this.auth.getToken()
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: SubscribeToDataResultDTO) =>
          OperationsMapper.dtoToModel(data)
        )
      );
  }
}
