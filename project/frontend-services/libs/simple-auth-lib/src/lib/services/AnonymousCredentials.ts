import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {FetchResult} from '@apollo/client/core';
import {AnonymousResultDTO} from '../dto/CredentialsDTO';

@Injectable({
  providedIn: 'root',
})
export class AnonymousCredentials {
  constructor(private apollo: Apollo) {
  }

  validate(): Observable<FetchResult<AnonymousResultDTO>> {
    const query = gql`
      query anonymous {
        anonymous {
          token
        }
      }
    `;

    return this.apollo.use('identity').subscribe<AnonymousResultDTO>({
      query,
    });
  }
}
