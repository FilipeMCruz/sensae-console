import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from '@frontend-services/core';
import { TenantInfo } from '@frontend-services/identity-management/model';
import {
  QueryMapper,
  TenantMapper,
} from '@frontend-services/identity-management/mapper';
import { AddTenantResultDTO } from '@frontend-services/identity-management/dto';

@Injectable({
  providedIn: 'root',
})
export class AddTenant {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  mutate(tenantId: string, domainId: string): Observable<TenantInfo> {
    const mutation = gql`
      mutation addTenant($instructions: AddTenantToDomain) {
        addTenant(instructions: $instructions) {
          oid
          email
          name
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<AddTenantResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toAddTenant(tenantId, domainId),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: AddTenantResultDTO) =>
          TenantMapper.dtoToModel(value.addTenant)
        )
      );
  }
}
