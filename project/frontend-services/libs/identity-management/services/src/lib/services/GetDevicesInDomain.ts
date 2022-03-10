import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {ViewDevicesInDomainResultDTO} from "@frontend-services/identity-management/dto";
import {DeviceMapper, QueryMapper} from "@frontend-services/identity-management/mapper";
import {DeviceInfo} from "@frontend-services/identity-management/model";

@Injectable({
  providedIn: 'root',
})
export class GetDevicesInDomain {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(domainId: string): Observable<Array<DeviceInfo>> {
    const query = gql`
      query viewDevicesInDomain($domain: ViewDomain){
        viewDevicesInDomain(domain: $domain){
          oid
          domains{
            oid
            permission
          }
        }
      }
    `;
    return this.apollo
      .use('identity')
      .query<ViewDevicesInDomainResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toViewDomain(domainId),
        fetchPolicy: 'no-cache',
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: ViewDevicesInDomainResultDTO) => data.viewDevicesInDomain.map(d => DeviceMapper.dtoToModel(d)))
      );
  }
}
