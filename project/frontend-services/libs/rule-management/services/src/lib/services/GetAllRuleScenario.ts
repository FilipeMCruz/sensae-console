import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {RuleScenario} from "@frontend-services/rule-management/model";
import {RuleScenarioQuery} from "@frontend-services/rule-management/dto";
import {RuleScenarioQueryMapper} from "@frontend-services/rule-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class GetAllRuleScenario {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<RuleScenario>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["rule_management:rules:read"]))
      return EMPTY;

    const query = gql`
      query scenario {
        scenario {
          id {
            value
          }
          content
          applied
        }
      }
    `;
    return this.apollo
      .use('dataDecoder')
      .query<RuleScenarioQuery>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        fetchPolicy: 'no-cache',
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: RuleScenarioQuery) =>
          RuleScenarioQueryMapper.dtoToModel(data)
        )
      );
  }
}
