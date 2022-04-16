import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {Data} from "@frontend-services/smart-irrigation/model";
import {QueryLatestDataResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";
import {LatestDataQueryFilters} from "@frontend-services/smart-irrigation/model";

@Injectable({
  providedIn: 'root',
})
export class FetchLatestData {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(filters: LatestDataQueryFilters): Observable<Array<Data>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:latest_data:read"]))
      return EMPTY;

    const query = gql`
      query fetchLatestData($filters: LatestDataQueryFilters){
        fetchLatestData(filters: $filters){
          dataId
          device{
            id
            name
            type
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
                percentage
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
                gramsPerCubicMeter
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
      .subscribe<QueryLatestDataResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.latestDataFiltersModelToDto(filters),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: QueryLatestDataResultDTO) => OperationsMapper.fetchLatestDataDtoToModel(data))
      );
  }
}
