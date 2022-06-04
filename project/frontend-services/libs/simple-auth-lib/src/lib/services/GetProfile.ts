import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {TenantProfileResultDTO} from '../dto/CredentialsDTO';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "@frontend-services/core";
import {ProfileMapper} from "../mapper/ProfileMapper";
import {TenantProfile} from "../model/TenantProfile";
import {AuthService} from "../AuthService";

@Injectable({
  providedIn: 'root',
})
export class GetProfile {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  fetch(): Observable<TenantProfile> {
    const query = gql`
      query profile {
        profile {
          oid
          email
          name
          phoneNumber
        }
      }
    `;

    return this.apollo.use('identity').subscribe<TenantProfileResultDTO>({
      query,
      context: {
        headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken()),
      },
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: TenantProfileResultDTO) => ProfileMapper.dtoToQueryModel(data)
      )
    );
  }
}
