import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {IrrigationZone} from "@frontend-services/smart-irrigation/model";
import {QueryIrrigationZonesResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class FetchIrrigationZones {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<IrrigationZone>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:garden:read"]))
      return EMPTY;

    const query = gql`
      query fetchIrrigationZones{
        fetchIrrigationZones{
          id
          name
          area{
            position
            longitude
            latitude
            altitude
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<QueryIrrigationZonesResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: QueryIrrigationZonesResultDTO) => OperationsMapper.fetchIrrigationZoneDtoToModel(data))
      );
  }
}
