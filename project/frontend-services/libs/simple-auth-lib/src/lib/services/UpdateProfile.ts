import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {TenantProfileResultDTO, TenantProfileUpdateResultDTO} from '../dto/CredentialsDTO';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "@frontend-services/core";
import {ProfileMapper} from "../mapper/ProfileMapper";
import {TenantProfile} from "../model/TenantProfile";
import {AuthService} from "../AuthService";

@Injectable({
  providedIn: 'root',
})
export class UpdateProfile {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  mutate(tenant: TenantProfile): Observable<TenantProfile> {
    const query = gql`
      mutation updateTenant($instructions: TenantUpdateCommandInput) {
        updateTenant(instructions: $instructions) {
          oid
          email
          name
          phoneNumber
        }
      }
    `;

    return this.apollo.use('identity').subscribe<TenantProfileUpdateResultDTO>({
      query,
      context: {
        headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken()),
      },
      variables: ProfileMapper.toUpdateTenant(tenant),
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: TenantProfileUpdateResultDTO) => ProfileMapper.dtoToUpdateModel(data)
      )
    );
  }
}
