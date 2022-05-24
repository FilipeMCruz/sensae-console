import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {RuleScenarioDelete} from "@frontend-services/rule-management/dto";
import {RuleScenario, RuleScenarioId} from "@frontend-services/rule-management/model";
import {RuleScenarioIdMapper} from "@frontend-services/rule-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class DeleteRuleScenario {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  delete(event: RuleScenario): Observable<RuleScenarioId> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["rule_management:rules:delete"]))
      return EMPTY;

    const mutation = gql`
      mutation delete($id: ScenarioIdInput) {
        delete(id: $id) {
          value
        }
      }
    `;
    return this.apollo
      .use('ruleManagement')
      .mutate<RuleScenarioDelete>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {type: RuleScenarioIdMapper.modelToDto(event.id)},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: RuleScenarioDelete) =>
          RuleScenarioIdMapper.dtoToModel(data.delete)
        )
      );
  }
}
