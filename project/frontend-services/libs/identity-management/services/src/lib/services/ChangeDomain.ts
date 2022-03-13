import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from '@frontend-services/core';
import { Domain } from '@frontend-services/identity-management/model';
import {
  DomainMapper,
  QueryMapper,
} from '@frontend-services/identity-management/mapper';
import { ChangeDomainResultDTO } from '@frontend-services/identity-management/dto';

@Injectable({
  providedIn: 'root',
})
export class ChangeDomain {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  mutate(
    domainId: string,
    name: string,
    permissions: string[]
  ): Observable<Domain> {
    const mutation = gql`
      mutation changeDomain($domain: CreateDomain) {
        changeDomain(domain: $domain) {
          oid
          name
          path
          permissions
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<ChangeDomainResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toChangeDomain(domainId, name, permissions),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: ChangeDomainResultDTO) =>
          DomainMapper.dtoToModel(value.changeDomain)
        )
      );
  }
}
