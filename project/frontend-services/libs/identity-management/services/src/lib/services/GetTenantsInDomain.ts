import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {ViewTenantsInDomainResultDTO} from "@frontend-services/identity-management/dto";
import {QueryMapper, TenantMapper} from "@frontend-services/identity-management/mapper";
import {TenantInfo} from "@frontend-services/identity-management/model";

@Injectable({
  providedIn: 'root',
})
export class GetTenantsInDomain {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(domainId: string): Observable<Array<TenantInfo>> {
    const query = gql`
      query viewTenantsInDomain($domain: ViewDomain){
        viewTenantsInDomain(domain: $domain){
          oid
          email
          name
        }
      }
    `;
    return this.apollo
      .use('identity')
      .query<ViewTenantsInDomainResultDTO>({
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
        map((data: ViewTenantsInDomainResultDTO) => data.viewTenantsInDomain.map(d => TenantMapper.dtoToModel(d)))
      );
  }
}
