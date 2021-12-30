import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {CredentialsDTO, OutcomeDTO, OutcomeDTOQuery} from "../dto/CredentialsDTO";

@Injectable({
  providedIn: 'root'
})
export class ValidateCredentials {

  constructor(private apollo: Apollo) {
  }

  validate(user: CredentialsDTO): Observable<FetchResult<OutcomeDTOQuery>> {
    const query = gql`
      query credentials($user: UserCredentials){
        credentials(user: $user){
          valid
        }
      }
    `;

    return this.apollo.use("simpleAuth").subscribe<OutcomeDTOQuery>({query, variables: {user}});
  }
}
