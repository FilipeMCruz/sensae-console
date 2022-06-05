import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {FetchResult} from '@apollo/client/core';
import {AuthenticateResultDTO} from '../dto/CredentialsDTO';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ValidateCredentials {
  constructor(private apollo: Apollo) {
  }

  validate(token: string, provider: string): Observable<FetchResult<AuthenticateResultDTO>> {
    const query = gql`
      query authenticate($provider: String) {
        authenticate(provider: $provider) {
          token
        }
      }
    `;

    return this.apollo.use('identity').subscribe<AuthenticateResultDTO>({
      query,
      context: {
        headers: new HttpHeaders().set('Authorization', 'Bearer ' + token),
      },
      variables: {
        provider: provider
      }
    });
  }
}
