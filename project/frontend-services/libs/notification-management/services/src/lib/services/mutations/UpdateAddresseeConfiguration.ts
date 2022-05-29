import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {AddresseeConfiguration} from "@frontend-services/notification-management/model";
import {AddresseeConfigMutationResultDTO} from "@frontend-services/notification-management/dto";
import {OperationsMapper} from "@frontend-services/notification-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class UpdateAddresseeConfiguration {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["notification_management:config:edit"]);
  }

  execute(command: AddresseeConfiguration): Observable<AddresseeConfiguration> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation config($instructions: AddresseeConfigCommandInput){
        config(instructions: $instructions){
          entries{
            deliveryType
            contentType{
              category
              subCategory
              level
            }
            mute
          }
        }
      }
    `;

    return this.apollo
      .use('notificationManagement')
      .subscribe<AddresseeConfigMutationResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.configAddresseeInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: AddresseeConfigMutationResultDTO) => OperationsMapper.configAddresseeMutationResult(data))
      );
  }
}
