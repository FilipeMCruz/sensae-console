import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {FetchResult} from '@apollo/client/core';
import {RefreshResultDTO} from '../dto/CredentialsDTO';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class RefreshAuthToken {
  constructor(private apollo: Apollo) {
  }

  refresh(user: string): Observable<FetchResult<RefreshResultDTO>> {
    const query = gql`
      query refresh {
        refresh {
          token
        }
      }
    `;

    return this.apollo.use('identity').subscribe<RefreshResultDTO>({
      query,
      context: {
        headers: new HttpHeaders().set('Authorization', 'Bearer ' + user),
      },
    });
  }
}
