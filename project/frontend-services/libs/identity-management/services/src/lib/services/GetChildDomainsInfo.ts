import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {ViewChildDomainInfoResultDTO} from '@frontend-services/identity-management/dto';
import {
  DomainMapper,
  QueryMapper,
} from '@frontend-services/identity-management/mapper';
import {DomainInfo} from '@frontend-services/identity-management/model';

@Injectable({
  providedIn: 'root',
})
export class GetChildDomainsInfo {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  query(domainId: string): Observable<Array<DomainInfo>> {
    const query = gql`
      query viewChildDomainsInfo($domain: ViewDomain) {
        viewChildDomainsInfo(domain: $domain) {
          domain {
            oid
            name
            path
            permissions
          }
          devices {
            oid
            name
            domains {
              oid
            }
          }
          tenants {
            oid
            email
            name
          }
        }
      }
    `;
    return this.apollo
      .use('identity')
      .query<ViewChildDomainInfoResultDTO>({
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
        map((data: ViewChildDomainInfoResultDTO) =>
          data.viewChildDomainsInfo.map((d) => DomainMapper.dtoDetailsToDto(d))
        )
      );
  }
}
