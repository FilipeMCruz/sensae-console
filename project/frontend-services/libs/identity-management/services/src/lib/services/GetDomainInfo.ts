import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from '@frontend-services/core';
import { ViewDomainInfoResultDTO } from '@frontend-services/identity-management/dto';
import {
  DomainMapper,
  QueryMapper,
} from '@frontend-services/identity-management/mapper';
import { DomainInfo } from '@frontend-services/identity-management/model';

@Injectable({
  providedIn: 'root',
})
export class GetDomainInfo {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  query(domainId: string): Observable<DomainInfo> {
    const query = gql`
      query viewDomainInfo($domain: ViewDomain) {
        viewDomainInfo(domain: $domain) {
          domain {
            oid
            name
            path
            permissions
          }
          devices {
            oid
            domains {
              oid
              permission
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
      .query<ViewDomainInfoResultDTO>({
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
        map((data: ViewDomainInfoResultDTO) =>
          DomainMapper.dtoDetailsToDto(data.viewDomainInfo)
        )
      );
  }
}
