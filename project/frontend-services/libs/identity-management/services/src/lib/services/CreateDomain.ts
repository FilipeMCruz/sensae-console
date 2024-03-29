import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {Domain} from '@frontend-services/identity-management/model';
import {
  DomainMapper,
  QueryMapper,
} from '@frontend-services/identity-management/mapper';
import {CreateDomainResultDTO} from '@frontend-services/identity-management/dto';

@Injectable({
  providedIn: 'root',
})
export class CreateDomain {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAllowed(Array.of("identity_management:domains:create"))
  }

  mutate(parentId: string, name: string): Observable<Domain> {
    if (!this.canDo())
      return EMPTY;

    const mutation = gql`
      mutation createDomain($domain: CreateDomain) {
        createDomain(domain: $domain) {
          oid
          name
          path
          permissions
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<CreateDomainResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toCreateDomain(parentId, name),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: CreateDomainResultDTO) =>
          DomainMapper.dtoToModel(value.createDomain)
        )
      );
  }
}
