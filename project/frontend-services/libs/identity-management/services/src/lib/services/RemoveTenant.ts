import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {TenantInfo} from "@frontend-services/identity-management/model";
import {QueryMapper, TenantMapper} from "@frontend-services/identity-management/mapper";
import {RemoveTenantResultDTO} from "@frontend-services/identity-management/dto";

@Injectable({
  providedIn: 'root',
})
export class RemoveTenant {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(tenantId: string, domainId: string): Observable<TenantInfo> {
    const mutation = gql`
      mutation removeTenant($instructions: RemoveTenantFromDomain){
        removeTenant(instructions: $instructions){
          oid
          email
          name
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<RemoveTenantResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toRemoveTenant(tenantId, domainId),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: RemoveTenantResultDTO) =>
          TenantMapper.dtoToModel(value.removeTenant)
        )
      );
  }
}
