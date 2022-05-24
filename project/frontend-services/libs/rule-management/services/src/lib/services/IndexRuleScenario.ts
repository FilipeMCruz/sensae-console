import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {RuleScenario} from "@frontend-services/rule-management/model";
import {RuleScenarioRegisterMapper} from "@frontend-services/rule-management/mapper";
import {RuleScenarioResult} from "@frontend-services/rule-management/dto";

@Injectable({
  providedIn: 'root',
})
export class IndexRuleScenario {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: RuleScenario): Observable<RuleScenario> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["rule_management:rules:edit"]))
      return EMPTY;

    const mutation = gql`
      mutation index($scenario: RuleScenarioInput) {
        index(scenario: $scenario) {
          id {
            value
          }
          content
          applied
        }
      }
    `;
    return this.apollo
      .use('ruleManagement')
      .mutate<RuleScenarioResult>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: RuleScenarioRegisterMapper.modelToDto(event),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: RuleScenarioResult) =>
          RuleScenarioRegisterMapper.dtoToModel(data)
        )
      );
  }
}
