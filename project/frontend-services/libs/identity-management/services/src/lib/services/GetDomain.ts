import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {ViewDomainResultDTO} from "@frontend-services/identity-management/dto";
import {DomainMapper} from "@frontend-services/identity-management/mapper";
import {Domain} from "@frontend-services/identity-management/model";
import {QueryMapper} from "@frontend-services/identity-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class GetDomain {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(domainId: string): Observable<Array<Domain>> {
    const query = gql`
      query viewDomain($domain: ViewDomain){
        viewDomain(domain: $domain){
          oid
          name
          path
        }
      }
    `;
    return this.apollo
      .use('identity')
      .query<ViewDomainResultDTO>({
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
        map((data: ViewDomainResultDTO) => data.viewDomain.map(d => DomainMapper.dtoToModel(d)))
      );
  }
}
